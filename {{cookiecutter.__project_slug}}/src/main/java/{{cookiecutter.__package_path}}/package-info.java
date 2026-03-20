@io.avaje.inject.InjectModule(
        name = "{{ cookiecutter.main_class_name }}"
{% if cookiecutter.use_xcore_plugin %},
        requiresPackages = {
                org.xcore.plugin.XcorePlugin.class
        }
{% endif %})
package {{ cookiecutter.package_name }};
