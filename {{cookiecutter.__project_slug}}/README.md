# {{ cookiecutter.plugin_display_name }}

{{ cookiecutter.description }}

## Requirements

- Java 25
- Mindustry v{{ cookiecutter.mindustry_version }}
{% if cookiecutter.use_xcore_plugin %}- XCore-plugin {{ cookiecutter.xcore_plugin_version }} or newer available on the server{% endif %}

## Build

```bash
./gradlew shadowJar
```

The generated jar will be available in `build/libs/`.

## Notes

- `plugin.json` is generated from Gradle metadata via `toxopid`.
{% if cookiecutter.use_xcore_plugin %}- The plugin expects XCore to be loaded first and reuses its Avaje container as parent scope.{% endif %}
{% if cookiecutter.use_flubundle %}- Localization bundles live in `src/main/resources/bundles/`.{% endif %}
