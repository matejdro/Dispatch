/*
 * Copyright (C) 2020 Rick Busarow
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

plugins {
  id(Plugins.androidLibrary)
  id(Plugins.kotlinAndroid)
  id(Plugins.kotlinAndroidExtensions)
  id(Plugins.dokka).version(Versions.dokka)
}

buildscript {

  repositories {
    mavenCentral()
    google()
    jcenter()
  }
  dependencies {
    classpath(BuildPlugins.kotlinGradlePlugin)
  }

}

android {
  compileSdkVersion(Versions.compileSdk)

  defaultConfig {
    minSdkVersion(Versions.minSdk)
    targetSdkVersion(Versions.targetSdk)
    versionCode = 1
    versionName = Versions.versionName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {

  lintPublish(project(":android-lifecycle-runtime-lint"))

  implementation(Libs.Androidx.lifecycle)
  testImplementation(Libs.Androidx.lifecycleRuntime)

  implementation(Libs.JakeWharton.timber)

  implementation(Libs.Kotlin.stdlib)

  implementation(Libs.Kotlinx.Coroutines.android)
  implementation(Libs.Kotlinx.Coroutines.core)

  implementation(project(":core"))
  implementation(project(":extensions"))
  testImplementation(project(":core-test"))
  testImplementation(project(":internal-test"))

  testImplementation(Libs.JUnit.jUnit5)
  testImplementation(Libs.KotlinTest.junit4runner)
  testImplementation(Libs.Kotlinx.Coroutines.test)
  testImplementation(Libs.RickBusarow.DispatcherProvider.test)

  testImplementation(Libs.Androidx.testRunner)
  testImplementation(Libs.Androidx.espresso)
}