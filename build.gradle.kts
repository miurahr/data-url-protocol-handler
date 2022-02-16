plugins {
    checkstyle
    jacoco
    signing
    `java-library`
    `java-library-distribution`
    `maven-publish`
    id("com.github.spotbugs") version "5.0.5"
    id("com.diffplug.spotless") version "6.3.0"
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("com.palantir.git-version") version "0.13.0"
}

// calculate version string from git tag, hash and commit distance
fun getVersionDetails(): com.palantir.gradle.gitversion.VersionDetails = (extra["versionDetails"] as groovy.lang.Closure<*>)() as com.palantir.gradle.gitversion.VersionDetails
if (getVersionDetails().isCleanTag) {
    version = getVersionDetails().lastTag.substring(1)
} else {
    version = getVersionDetails().lastTag.substring(1) + "-" + getVersionDetails().commitDistance + "-" + getVersionDetails().gitHash + "-SNAPSHOT"
}

java {
    withSourcesJar()
    withJavadocJar()
}

group = "tokyo.northside"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-codec:commons-codec:1.15")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

jacoco {
    toolVersion="0.8.6"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

spotbugs {
    excludeFilter.set(project.file("config/spotbugs/exclude.xml"))
    tasks.spotbugsMain {
        reports.create("html") {
            required.set(true)
        }
    }
    tasks.spotbugsTest {
        reports.create("html") {
            required.set(true)
        }
    }
}

coveralls {
    jacocoReportPath = "build/reports/jacoco/test/jacocoTestReport.xml"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
    options.compilerArgs.add("-Xlint:unchecked")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("URL protocol handler")
                description.set("URL protocol handler for java")
                url.set("https://github.com/miurahr/url-protocol-handler")
                licenses {
                    license {
                        name.set("The GNU General Public License, Version 3")
                        url.set("https://www.gnu.org/licenses/licenses/gpl-3.html")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("miurahr")
                        name.set("Hiroshi Miura")
                        email.set("miurahr@linux.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/miurahr/url-protocol-handler.git")
                    developerConnection.set("scm:git:git://github.com/miurahr/url-protocol-handler.git")
                    url.set("https://github.com/miurahr/url-protocol-handler")
                }
            }
        }
    }
}

signing {
    if (project.hasProperty("signingKey")) {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    } else {
        useGpgCmd()
    }
    sign(publishing.publications["mavenJava"])
}
tasks.withType<Sign> {
    val hasKey = project.hasProperty("signingKey") || project.hasProperty("signing.gnupg.keyName")
    onlyIf { hasKey && getVersionDetails().isCleanTag }
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("SONATYPE_USER"))
            password.set(System.getenv("SONATYPE_PASS"))
        }
    }
}
