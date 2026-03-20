rootProject.name = "{{ cookiecutter.__project_slug }}"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
        maven("https://www.jitpack.io")
{% if cookiecutter.use_xcore_plugin or cookiecutter.use_flubundle %}
        maven("https://maven.x-core.org/releases")
        maven("https://maven.x-core.org/snapshots")
{% endif %}
    }
}
