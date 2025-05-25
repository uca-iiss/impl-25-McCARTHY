plugins {
    kotlin("jvm") version "1.9.22"
    application
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}


tasks.test {
    useJUnit()
}
