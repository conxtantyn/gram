
android { namespace = "com.gram.core" }

dependencies {
    implementation(project(Module.common))

    Dependency.navigation.forEach((::api))

    Dependency.daggerProcessor.forEach((::kapt))
}
