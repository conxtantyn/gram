
android {
    namespace = "com.gram.database"

    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }
}

dependencies {
    implementation(project(Module.common))
    implementation(project(Module.gallery))

    Dependency.room.forEach((::implementation))
    Dependency.roomCompiler.forEach((::kapt))
    Dependency.daggerProcessor.forEach((::kapt))

    Dependency.test.forEach((::testImplementation))
    Dependency.androidTest.forEach((::androidTestImplementation))
}
