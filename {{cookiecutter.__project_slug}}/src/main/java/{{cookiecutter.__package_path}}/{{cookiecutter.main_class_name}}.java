package {{ cookiecutter.package_name }};

import arc.util.Log;
{% if cookiecutter.use_flubundle %}import com.ospx.flubundle.Bundle;
{% endif %}import io.avaje.inject.BeanScope;
import mindustry.mod.Plugin;
{% if cookiecutter.use_xcore_plugin %}import org.xcore.plugin.XcorePlugin;
{% endif %}
public class {{ cookiecutter.main_class_name }} extends Plugin {

    private BeanScope scope;

    @Override
    public void init() {
        Log.info("[{{ cookiecutter.plugin_display_name }}] Initializing...");
{% if cookiecutter.use_xcore_plugin %}
        var parent = XcorePlugin.container;
        if (parent == null) {
            throw new IllegalStateException("XCore container is not available. Make sure XCore-plugin is loaded first.");
        }
{% endif %}
        scope = BeanScope.builder()
                .classLoader(getClass().getClassLoader())
{% if cookiecutter.use_xcore_plugin %}
                .parent(parent)
{% endif %}
                .build();
{% if cookiecutter.use_flubundle %}
        var bundle = scope.get(Bundle.class);
        bundle.addSource(getClass());
{% endif %}
        new PluginBootstrap().start();
        Log.info("[{{ cookiecutter.plugin_display_name }}] Started successfully.");
    }
}
