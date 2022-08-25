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

@file:OptIn(ExperimentalCoroutinesApi::class)

package dispatch.test.samples

import dispatch.test.TestDispatcherProvider
import dispatch.test.coroutineTestExtension
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class RegisterWithFactorySample {

  @JvmField
  @RegisterExtension
  val extension = coroutineTestExtension {
    TestScope(context = CoroutineName("custom name") + TestDispatcherProvider())
  }

  @Test
  fun `extension should provide a scope from the custom factory`() = runBlocking {

    extension.scope.coroutineContext[CoroutineName] shouldBe CoroutineName("custom name")
  }
}
