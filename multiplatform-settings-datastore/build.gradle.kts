/*
 * Copyright 2019 Russell Wolf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

standardConfiguration("android")

kotlin {
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":multiplatform-settings"))
                implementation(project(":multiplatform-settings-coroutines"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))

                implementation(project(":tests"))
                implementation(project(":multiplatform-settings-test"))

                implementation("app.cash.turbine:turbine:${Versions.turbine}")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.datastore:datastore-preferences-core:${Versions.androidxDatastore}")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))

                implementation("junit:junit:${Versions.junit}")
                implementation("androidx.test:core:${Versions.androidxTest}")
                implementation("androidx.test.ext:junit:${Versions.androidxTestExt}")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")

            }
        }
    }
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
}