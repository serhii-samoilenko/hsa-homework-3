plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
//    Native build takes enormous amount of resources
//    id("org.graalvm.buildtools.native")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
//    Later
//    implementation("io.zipkin.reporter2:zipkin-reporter-brave")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.openfeign:feign-core:12.1")
    implementation("io.github.openfeign:feign-jackson:12.1")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    runtimeOnly("io.micrometer:micrometer-registry-statsd")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
}
