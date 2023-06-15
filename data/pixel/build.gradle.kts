
android {
    namespace = "com.gram.pixel"

    productFlavors["mock"]?.withGroovyBuilder {
        dependencies {
            implementation(Dependency.faker)
        }
    }
}

dependencies {
    implementation(project(Module.common))
    implementation(project(Module.gallery))

    Dependency.network.forEach((::implementation))
    Dependency.daggerProcessor.forEach((::kapt))

    Dependency.test.forEach((::testImplementation))
}
