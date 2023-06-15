
android {
    defaultConfig {
        applicationId = "com.gram.app"
    }
}

dependencies {
    implementation(project(Module.common))
    implementation(project(Module.core))
    implementation(project(Module.gallery))
    implementation(project(Module.database))
    implementation(project(Module.pixel))

    Dependency.utils.forEach((::implementation))

    Dependency.daggerProcessor.forEach((::kapt))

    Dependency.test.forEach((::testImplementation))
    Dependency.androidTest.forEach((::debugImplementation))
}
