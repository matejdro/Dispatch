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

package dispatch.test

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Suppress("HardCodedDispatcher")
@ExperimentalCoroutinesApi
internal class TestDispatcherProviderTest {

  @Nested
  inner class `TestCoroutineDispatcher factory` {

    @Test
    fun `provider arg should be assigned to all properties`() {

      val dispatcher = StandardTestDispatcher()

      val provider = TestDispatcherProvider(dispatcher)

      provider.default shouldBe dispatcher
      provider.io shouldBe dispatcher
      provider.main shouldBe dispatcher
      provider.mainImmediate shouldBe dispatcher
      provider.unconfined shouldBe dispatcher
    }

    @Test
    fun `factory should create TestDispatcherProvider`() {

      val dispatcher = StandardTestDispatcher()

      val provider = TestDispatcherProvider(dispatcher)

      provider.shouldBeTypeOf<TestDispatcherProvider>()
    }
  }
}
