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

import dispatch.core.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Delegates to [runBlocking], but injects a [DispatcherProvider] into the created [CoroutineScope].
 *
 * The resultant [CoroutineContext] will use a
 * [BlockingEventLoop][kotlinx.coroutines.BlockingEventLoop]
 * as its default [ContinuationInterceptor].
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the [kotlinx.coroutines.BlockingEventLoop] interceptor.
 *
 * @param context The base `CoroutineContext` which will be modified to use a
 *     [TestDispatcherProvider]. [EmptyCoroutineContext] is used if
 *     one is not provided.
 * @param block the action to be performed
 * @sample dispatch.test.samples.BuildersSample.runBlockingProvidedSample
 * @see runBlocking
 * @see testProvided
 */
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
public fun runBlockingProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  block: suspend CoroutineScope.() -> Unit
): Unit = runBlocking(context) {

  val existingDispatcherProvider = context[DispatcherProvider]

  val newContext = if (existingDispatcherProvider == null) {
    coroutineContext + DispatcherProvider()
  } else coroutineContext

  CoroutineScope(newContext).block()
}

/**
 * Delegates to [runBlockingTest], but injects a [DispatcherProvider] into the created
 * [TestCoroutineScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a [TestCoroutineDispatcher], then a new
 * [TestCoroutineDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestCoroutineDispatcher`.
 *
 * @param context The base `CoroutineContext` which will be modified to use a
 *     `TestCoroutineDispatcher` and `TestDispatcherProvider`. [EmptyCoroutineContext] is used if
 *     one is not provided.
 * @param testBody the action to be performed
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @see runBlockingTest
 * @see runBlockingProvided
 */
@Deprecated("Deprecated since TestCoroutineDispatcher and runBlockingTest are deprecated.")
@ExperimentalCoroutinesApi
public fun testProvided(
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {

  val existingDispatcher = context[ContinuationInterceptor]

  val dispatcher = existingDispatcher as? TestDispatcher ?: StandardTestDispatcher()

  val dispatcherProvider = context[DispatcherProvider]
    ?: TestDispatcherProvider(dispatcher)

  val combinedContext = context + dispatcher + dispatcherProvider

  return runTest(context = combinedContext, testBody = testBody)
}

@Deprecated("Deprecated since TestCoroutineDispatcher and runBlockingTest are deprecated.")
@ExperimentalCoroutinesApi
public fun testProvidedUnconfined(
  context: CoroutineContext = EmptyCoroutineContext,
  testBody: suspend TestScope.() -> Unit
) {

  val dispatcher = UnconfinedTestDispatcher()

  val dispatcherProvider = TestDispatcherProvider(dispatcher)

  val combinedContext = context + dispatcher + dispatcherProvider

  return runTest(context = combinedContext, testBody = testBody)
}

/**
 * Delegates to [runBlockingTest], but injects a [DispatcherProvider] into the created
 * [TestCoroutineScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a [TestCoroutineDispatcher], then a new
 * [TestCoroutineDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestCoroutineDispatcher`.
 *
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @sample dispatch.test.samples.BuildersSample.testProvidedExtensionSample
 * @see runBlockingTest
 * @see runBlockingProvided
 */
@Deprecated("Deprecated since TestCoroutineDispatcher and runBlockingTest are deprecated.")
@ExperimentalCoroutinesApi
public fun TestProvidedCoroutineScope.testProvided(
  testBody: suspend TestScope.() -> Unit
): Unit = testProvided(coroutineContext, testBody)

/**
 * Delegates to [runBlockingTest], but injects a [DispatcherProvider] into the created
 * [TestCoroutineScope].
 *
 * If the `context`'s [ContinuationInterceptor] is not a [TestCoroutineDispatcher], then a new
 * [TestCoroutineDispatcher] will be created.
 *
 * If the `context` does not contain a `DispatcherProvider`, a [TestDispatcherProvider] will be
 * created using the `TestCoroutineDispatcher`.
 *
 * @sample dispatch.test.samples.BuildersSample.testProvidedSample
 * @sample dispatch.test.samples.BuildersSample.testProvidedExtensionSample
 * @see runBlockingTest
 * @see runBlockingProvided
 */
@Deprecated("Deprecated since TestCoroutineDispatcher and runBlockingTest are deprecated.")
@ExperimentalCoroutinesApi
public fun TestScope.testProvided(
  testBody: suspend TestScope.() -> Unit
): Unit = testProvided(coroutineContext, testBody)
