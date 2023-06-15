
android {
    namespace = "com.gram.common"
}

dependencies {
    Dependency.dagger.forEach((::api))
    Dependency.common.forEach((::api))
}
