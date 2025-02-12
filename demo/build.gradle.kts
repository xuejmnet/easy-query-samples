plugins {
    kotlin("jvm") version "1.9.21"
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib"))
    implementation("com.easy-query:sql-core:2.5.5")
    implementation("com.easy-query:sql-mysql:2.5.5")
    implementation("com.easy-query:sql-api-proxy:2.5.5")
//    annotationProcessor("com.easy-query:sql-processor:2.5.5")
    ksp("com.easy-query:sql-ksp-processor:2.5.5")
    implementation("com.mysql:mysql-connector-j:9.2.0")
    implementation("com.zaxxer:HikariCP:3.3.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
    // 将生成的代码添加到编译路径中。
    // 没有这个配置，gradle命令仍然可以正常执行，
    // 但是, Intellij无法找到生成的源码。
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
