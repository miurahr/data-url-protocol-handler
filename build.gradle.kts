plugins {
    java
    checkstyle
    jacoco
    id("com.github.spotbugs") version "4.7.3"
    id("com.diffplug.spotless") version "5.14.3"
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("com.palantir.git-version") version "0.12.3"
}

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
if (details.isCleanTag) {
    version = details.lastTag.substring(1)
} else {
    version = details.lastTag.substring(1) + "-" + details.commitDistance + "-" + details.gitHash + "-SNAPSHOT"
}

tasks.register("writeVersionFile") {
    val folder = project.file("src/main/resources");
    if (!folder.exists()) {
        folder.mkdirs()
    }
    val props = project.file("src/main/resources/version.properties")
    props.delete()
    props.appendText("version=" + project.version + "\n")
    props.appendText("commit=" + details.gitHashFull + "\n")
    props.appendText("branch=" + details.branchName)
}

tasks.getByName("jar") {
    dependsOn("writeVersionFile")
}

group = "tokyo.northside"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-codec:commons-codec:1.15")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

jacoco {
    toolVersion="0.8.6"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true  // coveralls plugin depends on xml format report
        html.isEnabled = true
    }
}

coveralls {
    jacocoReportPath = "build/reports/jacoco/test/jacocoTestReport.xml"
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
    options.compilerArgs.add("-Xlint:unchecked")
}
