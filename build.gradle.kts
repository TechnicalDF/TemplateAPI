plugins {
    id("java")
}

group = "dev.akarah"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.compileJava {
    options.compilerArgs.add("--enable-preview")
}

tasks.compileTestJava {
    options.compilerArgs.add("--enable-preview")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.java-websocket:Java-WebSocket:1.5.7")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}