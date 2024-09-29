plugins {
    id("java")
    id("maven-publish")
}

group = "dev.akarah"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name = "TemplateAPI"
                description = "API for creating DiamondFire templates"
                url = "https://akarah.dev"

                scm {
                    url = "https://github/akarahdev/TemplateAPI"
                }

                issueManagement {
                    system = "github"
                    url = "https://github.com/akarahdev/TemplateAPI/issues"
                }
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
            }
        }
    }
}