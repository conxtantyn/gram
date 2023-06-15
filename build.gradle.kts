plugins {
    val kotlinVersion = "1.8.0"
    val androidVersion = "7.4.2"
    val ktlintVersion = "10.3.0"
    val detektVersion = "1.21.0-RC2"

    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(androidVersion).apply(false)
    id("com.android.library").version(androidVersion).apply(false)

    id("org.jlleitschuh.gradle.ktlint").version(ktlintVersion).apply(false)
    id("io.gitlab.arturbosch.detekt").version(detektVersion).apply(false)

    kotlin("android").version(kotlinVersion).apply(false)
    kotlin("kapt").version(kotlinVersion).apply(false)
}

apply { from("gradle/common.gradle") }
