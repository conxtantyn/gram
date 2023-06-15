object Dependency {
    val common = listOf(
        "com.google.code.gson:gson:${Versions.gson}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}",
    )

    val dagger = listOf(
        "com.google.dagger:dagger-android:${Versions.dagger}",
        "com.google.dagger:dagger-android-support:${Versions.dagger}",
    )

    val daggerProcessor = listOf(
        "com.google.dagger:dagger-compiler:${Versions.dagger}",
        "com.google.dagger:dagger-android-processor:${Versions.dagger}",
    )

    val navigation = listOf(
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}",
    )

    val room = listOf(
        "androidx.room:room-ktx:${Versions.room}",
        "androidx.room:room-runtime:${Versions.room}",
    )

    val roomCompiler = listOf(
        "androidx.room:room-compiler:${Versions.room}",
    )

    val network = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
    )

    val utils = listOf(
        "com.github.bumptech.glide:glide:${Versions.glide}",
    )

    val test = listOf(
        "junit:junit:${Versions.junit}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}",
        "androidx.arch.core:core-testing:${Versions.androidxCore}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}",
        "io.mockk:mockk:${Versions.mockk}",
        "com.github.blocoio:faker:${Versions.faker}",
    )

    val androidTest = listOf(
        "androidx.test.ext:junit:${Versions.androidxJunit}",
        "androidx.arch.core:core-testing:${Versions.androidxCore}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}",
        "androidx.test.espresso:espresso-core:${Versions.espresso}",
        "androidx.test.espresso:espresso-contrib:${Versions.espresso}",
        "androidx.fragment:fragment-testing:${Versions.fragment}",
        "androidx.fragment:fragment-ktx:${Versions.fragment}",
        "androidx.test:runner:${Versions.runner}",
        "org.mockito:mockito-android:${Versions.mockito}",
        "org.mockito.kotlin:mockito-kotlin:${Versions.kotlinMockito}",
        "com.github.blocoio:faker:${Versions.faker}",
    )

    val faker = "com.github.blocoio:faker:${Versions.faker}"

    object Versions {
        const val dagger = "2.45"
        const val navigation = "2.5.3"
        const val kotlin = "1.8.20"
        const val coroutine = "1.6.0"
        const val androidxCore = "2.0.0"
        const val junit = "4.13.2"
        const val runner = "1.5.2"
        const val androidxJunit = "1.1.3"
        const val espresso = "3.5.1"
        const val mockk = "1.12.4"
        const val mockito = "5.3.0"
        const val kotlinMockito = "3.2.0"
        const val faker = "1.2.9"
        const val room = "2.4.2"
        const val glide = "4.12.0"
        const val compose = "1.3.1"
        const val composeActivity = "1.6.1"
        const val composeNavigation = "2.6.0-alpha09"
        const val fragment = "1.5.7"
        const val gson = "2.8.6"
        const val retrofit = "2.9.0"
    }
}
