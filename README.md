# XCore Plugin Template

Cookiecutter template for Java 25 Mindustry plugins in the XCore ecosystem.

What you get:
- Gradle Kotlin DSL
- `toxopid` with generated `plugin.json`
- `shadowJar`
- Avaje Inject + Lombok
- optional `XCore-plugin` dependency
- optional `FluBundle` dependency
- GitHub Actions build workflow
- Gradle wrapper included

## Prerequisites

- Python 3
- `cookiecutter`
- Java 25

Install Cookiecutter:

```bash
pip install cookiecutter
```

If your system Python does not provide `pip`, install Cookiecutter with your package manager or create a virtual environment first.

## Usage

Generate a new plugin from GitHub:

```bash
cookiecutter gh:XCore-mindustry/xcore-plugin-template
```

Generate from a local checkout:

```bash
cookiecutter /path/to/xcore-plugin-template
```

## Template Options

- `use_xcore_plugin=true` adds `compileOnly` dependency on `XCore-plugin` and declares an XCore mod dependency in generated metadata.
- `use_flubundle=true` adds `FluBundle`; when XCore is enabled it uses `compileOnly`, otherwise it uses `implementation` so the classes are bundled into the plugin jar.
- `plugin.json` is generated through `toxopid` metadata in `build.gradle.kts`.
- The template uses Cookiecutter features directly: conditional Jinja blocks, derived private variables, prompts via `__prompts__`, and `_copy_without_render` for the Gradle wrapper binary assets.

## After Generation

```bash
cd your-plugin-directory
./gradlew shadowJar
```

The built jar will be written to `build/libs/`.

## Included Defaults

- Java 25 toolchain
- `toxopid`, `shadow`, Avaje Inject, and Lombok
- generated `plugin.json`
- starter `PluginBootstrap` service
- starter localization bundle when `use_flubundle=true`
- GitHub Actions build workflow without Maven publish by default
