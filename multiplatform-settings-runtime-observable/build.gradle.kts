import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.konan.target.Family

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

plugins {
    id("standard-configuration")
    id("module-publication")
}

kotlin {

    androidTarget {
        publishAllLibraryVariants()
    }
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    js {
        browser()
    }
    jvm()
    macosArm64()
    macosX64()
    mingwX64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    watchosArm32()
    watchosArm64()
    watchosDeviceArm64()
    watchosSimulatorArm64()
    watchosX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":multiplatform-settings"))
            }
        }
        commonTest {
            dependencies {
                implementation(project(":multiplatform-settings-test"))
                implementation(project(":tests"))

                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.russhwolf.settings.runtime_observable"
}

