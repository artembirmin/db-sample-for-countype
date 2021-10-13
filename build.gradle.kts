import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
    `java-library`
    java
}

group = "com.incetro"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()

}


dependencies {
    testImplementation(kotlin("test"))
    implementation("org.ktorm:ktorm-core:3.4.1")
    // https://mvnrepository.com/artifact/org.apache.derby/derby
    implementation("org.apache.derby:derby:10.13.1.1")

}

tasks.test {
    useJUnitPlatform()
}

tasks.wrapper {
    gradleVersion = "6.1.1"
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
