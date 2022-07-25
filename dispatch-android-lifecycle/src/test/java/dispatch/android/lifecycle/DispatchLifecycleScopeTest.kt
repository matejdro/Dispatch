/*
 * Copyright (C) 2022 Rick Busarow
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dispatch.android.lifecycle

import androidx.lifecycle.Lifecycle
import dispatch.core.ioDispatcher
import dispatch.internal.test.BaseTest
import dispatch.internal.test.android.FakeLifecycleOwner
import dispatch.internal.test.android.LiveDataTest
import dispatch.test.TestProvidedCoroutineScope
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.isActive
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.ContinuationInterceptor

@FlowPreview
@ExperimentalCoroutinesApi
class DispatchLifecycleScopeTest :
  BaseTest(),
  LiveDataTest {

  val testScope by resets { TestProvidedCoroutineScope(context = Job() + mainDispatcher) }

  val lifecycleOwner by resets { FakeLifecycleOwner() }
  val lifecycle by resets { lifecycleOwner.lifecycle }
  val lifecycleScope by resets { DispatchLifecycleScope(lifecycle, testScope) }

  @Nested
  inner class cancellation {

    @Test
    fun `scope with Job should cancel on init if lifecycle is destroyed`() = runBlocking {

      lifecycleOwner.destroy()

      val scope = DispatchLifecycleScope(lifecycle, testScope)

      scope.isActive shouldBe false
    }

    @Test
    fun `scope should cancel when lifecycle is destroyed`() = runBlocking {

      lifecycleOwner.create()

      val scope = DispatchLifecycleScope(lifecycle, testScope)

      scope.isActive shouldBe true

      lifecycleOwner.destroy()

      scope.isActive shouldBe false
    }

    @Test
    fun `lifecycle observer should be removed when scope is cancelled`() = runBlocking {

      val lifecycleOwner = FakeLifecycleOwner()
      val lifecycle = lifecycleOwner.lifecycle
      val job = Job()
      lifecycleOwner.create()

      DispatchLifecycleScope(lifecycle, this + job + mainDispatcher)
      Thread.yield()
      yield()

      lifecycle.observerCount shouldBe 1

      job.cancel()
      Thread.yield()
      yield()

      lifecycle.observerCount shouldBe 0
    }
  }

  @Nested
  inner class `launch on create` {

    @Test
    fun `block should immediately execute if already created`() = runBlocking {

      lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

      var executed = false

      lifecycleScope.launchOnCreate { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if screen is not created`() = runBlocking {

      var executed = false

      lifecycleScope.launchOnCreate { executed = true }

      executed shouldBe false
    }

    @Test
    fun `block context should respect context parameter`() = runBlocking {

      lifecycleOwner.create()

      var dispatcher: ContinuationInterceptor? = null

      lifecycleScope.launchOnCreate(testScope.ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe testScope.ioDispatcher
    }

    @Test
    fun `block should stop when screen is destroyed`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.create()

      lifecycleScope.launchOnCreate {
        input.consumeAsFlow()
          .onCompletion { completed = true }
          .collect { output.add(it) }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycleOwner.destroy()

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }
  }

  @Nested
  inner class `launch on start` {

    @Test
    fun `block should immediately execute if already started`() = runBlocking {

      lifecycleOwner.start()

      var executed = false

      lifecycleScope.launchOnStart { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if screen is not started`() = runBlocking {

      Lifecycle.Event.values()
        .filter {
          when (it) {
            Lifecycle.Event.ON_CREATE -> true
            Lifecycle.Event.ON_STOP -> true
            Lifecycle.Event.ON_DESTROY -> true
            else -> false
          }
        }
        .forEach { event ->

          lifecycle.handleLifecycleEvent(event)

          var executed = false

          lifecycleScope.launchOnStart { executed = true }

          executed shouldBe false
        }
    }

    @Test
    fun `block context should respect context parameter`() = runBlocking {

      lifecycleOwner.start()

      var dispatcher: ContinuationInterceptor? = null

      lifecycleScope.launchOnStart(testScope.ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe testScope.ioDispatcher
    }

    @Test
    fun `block should stop when screen is stopped`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.start()

      lifecycleScope.launchOnStart {
        input.consumeAsFlow()
          .onCompletion { completed = true }
          .collect { output.add(it) }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycleOwner.stop()

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }
  }

  @Nested
  inner class `launch on resume` {

    @Test
    fun `block should immediately execute if already resumed`() = runBlocking {

      lifecycleOwner.resume()

      var executed = false

      lifecycleScope.launchOnResume { executed = true }

      executed shouldBe true
    }

    @Test
    fun `block should not immediately execute if screen is not resumed`() = runBlocking {

      Lifecycle.Event.values()
        .filter {
          when (it) {
            Lifecycle.Event.ON_CREATE -> true
            Lifecycle.Event.ON_START -> true
            Lifecycle.Event.ON_PAUSE -> true
            Lifecycle.Event.ON_STOP -> true
            Lifecycle.Event.ON_DESTROY -> true
            else -> false
          }
        }
        .forEach { event ->

          lifecycle.handleLifecycleEvent(event)

          var executed = false

          lifecycleScope.launchOnResume { executed = true }

          executed shouldBe false
        }
    }

    @Test
    fun `block context should respect context parameter`() = runBlocking {

      lifecycleOwner.resume()

      var dispatcher: ContinuationInterceptor? = null

      lifecycleScope.launchOnResume(testScope.ioDispatcher) {
        dispatcher = coroutineContext[ContinuationInterceptor]
      }

      dispatcher shouldBe testScope.ioDispatcher
    }

    @Test
    fun `block should stop when screen is paused`() = runBlocking {

      val input = Channel<Int>()
      val output = mutableListOf<Int>()
      var completed = false

      lifecycleOwner.resume()

      lifecycleScope.launchOnResume {
        input.consumeAsFlow()
          .onCompletion { completed = true }
          .collect { output.add(it) }
      }

      input.send(1)
      input.send(2)
      input.send(3)

      lifecycleOwner.pause()

      output shouldBe listOf(1, 2, 3)
      completed shouldBe true
    }
  }
}
