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

plugins {
  `java-platform`
  id("com.vanniktech.maven.publish")
}

version = libs.versions.versionName.get()

val bomProject = project

rootProject
  .subprojects
  .filter { it.includeInBom() }
  .forEach { bomProject.evaluationDependsOn(it.path) }

dependencies {
  constraints {
    rootProject
      .subprojects
      .filter { it.includeInBom() }
      .forEach { api(project(it.path)) }
  }
}

publishing {
  publications {
    create<MavenPublication>("DispatchBom") {
      from(components["javaPlatform"])
    }
  }
}


fun Project.includeInBom() = !path.contains("sample") &&
    !path.contains("internal") &&
    this != bomProject