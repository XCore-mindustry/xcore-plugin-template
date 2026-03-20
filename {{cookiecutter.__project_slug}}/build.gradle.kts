import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.xpdustry.toxopid.Toxopid
import com.xpdustry.toxopid.extension.anukeXpdustry
import com.xpdustry.toxopid.spec.ModDependency
import com.xpdustry.toxopid.spec.ModMetadata
import com.xpdustry.toxopid.spec.ModPlatform
import com.xpdustry.toxopid.task.MindustryExec
import org.gradle.api.tasks.testing.Test

plugins {
    java
    alias(libs.plugins.avaje.inject)
    alias(libs.plugins.shadow)
    alias(libs.plugins.toxopid)
}

group = "{{ cookiecutter.group_id }}"
version = "0.1.0-SNAPSHOT"

val mindustryVersion = libs.versions.mindustry.get()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get().toInt()))
    }
}

toxopid {
    compileVersion.set("v$mindustryVersion")
    runtimeVersion.set("v$mindustryVersion")
    platforms = setOf(ModPlatform.SERVER)
}

val metadataDependencies = mutableListOf<ModDependency>().apply {
{% if cookiecutter.use_xcore_plugin %}
    add(ModDependency("xcore-plugin"))
{% endif %}
}

val metadata = ModMetadata(
    name = "{{ cookiecutter.artifact_id }}",
    displayName = "{{ cookiecutter.plugin_display_name }}",
    description = "{{ cookiecutter.description }}",
    author = "{{ cookiecutter.author }}",
    version = project.version.toString(),
    minGameVersion = mindustryVersion,
    mainClass = "{{ cookiecutter.__main_class_fqcn }}",
    dependencies = metadataDependencies
)

dependencies {
    compileOnly(toxopid.dependencies.arcCore)
    compileOnly(toxopid.dependencies.mindustryCore)
    compileOnly(toxopid.dependencies.mindustryHeadless)
{% if cookiecutter.use_xcore_plugin %}
    compileOnly(libs.xcore.plugin)
    compileOnly(libs.cloud.mindustry)
{% endif %}
{% if cookiecutter.use_flubundle %}
    {{ cookiecutter.__flubundle_scope }}(libs.flubundle)
{% endif %}
    compileOnly(libs.avaje.inject)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.avaje.inject.gen)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor(libs.avaje.inject.gen)
}

val generatePluginJson by tasks.registering {
    val pluginFile = temporaryDir.resolve("plugin.json")
    inputs.property("metadata", ModMetadata.toJson(metadata, true))
    outputs.file(pluginFile)

    doLast {
        pluginFile.writeText(ModMetadata.toJson(metadata, true))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.named<Jar>("jar") {
    archiveFileName.set("${project.name}.jar")
    from(generatePluginJson)
}

fun ShadowJar.applyCommonSettings() {
    archiveFileName.set("${project.name}.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    mergeServiceFiles()
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
    from(generatePluginJson)
    configurations = listOf(project.configurations.runtimeClasspath.get())
    from(project.sourceSets.main.get().output)
}

tasks.named<ShadowJar>("shadowJar") {
    applyCommonSettings()
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

tasks.register("getProjectVersion") {
    doLast {
        println(project.version.toString())
    }
}

tasks.withType<MindustryExec> {
    group = Toxopid.TASK_GROUP_NAME
    classpath(tasks.downloadMindustryServer)
    mainClass.set("mindustry.server.ServerLauncher")
    modsDirPath.convention("./config/mods")
    standardInput = System.`in`
    mods.setFrom(tasks.shadowJar.map { it.archiveFile })
}

tasks.register("runServer", MindustryExec::class)

tasks.named<MindustryExec>("runServer") {
    workingDir = file("./server/runServer")
    doFirst {
        workingDir.mkdirs()
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
