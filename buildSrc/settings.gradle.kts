/*
 * Copyright (C) 2021 Rick Busarow
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

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
  }
  resolutionStrategy {
    eachPlugin {
      when {
        requested.id.id.startsWith("org.jetbrains.kotlin") -> useVersion("1.5.0")
      }
    }
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    google()
    mavenCentral()
    jcenter {
      content {
        // dokka
        includeGroup("org.jetbrains.dokka")
        includeModule("org.jetbrains", "markdown")
        // Detekt && dokka
        includeModule("org.jetbrains.kotlinx", "kotlinx-html-jvm")
        // https://youtrack.jetbrains.com/issue/IDEA-261387
        includeModule("org.jetbrains.trove4j", "trove4j")
      }
    }
  }
}

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}