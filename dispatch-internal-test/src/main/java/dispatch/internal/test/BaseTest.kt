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

package dispatch.internal.test

import hermit.test.junit.HermitJUnit5
import io.kotest.matchers.shouldBe
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

@OptIn(ObsoleteCoroutinesApi::class, ExperimentalCoroutinesApi::class)
public abstract class BaseTest : HermitJUnit5() {

  private val index = atomic(0)
  private val finished = atomic(false)

  public val mainDispatcher: ExecutorCoroutineDispatcher by resets {
    newSingleThreadContext("main")
      .also { Dispatchers.setMain(it) }
  }

  @BeforeEach
  internal fun _beforeEach() {
    mainDispatcher
  }

  @AfterEach
  internal fun _afterEach() {
    Dispatchers.resetMain()
    resetIndex()
  }

  public fun expect(expectedIndex: Int) {
    val actualIndex = index.incrementAndGet()

    actualIndex shouldBe expectedIndex
  }

  public fun finish(expectedIndex: Int) {
    expect(expectedIndex)
    require(!finished.getAndSet(true)) { "Should call 'finish(...)' at most once" }
  }

  private fun resetIndex() {
    try {
      require(index.value == 0 || finished.value) {
        "Expecting that 'finish(...)' was invoked, but it was not"
      }
    } finally {
      index.value = 0
      finished.value = false
    }
  }
}
