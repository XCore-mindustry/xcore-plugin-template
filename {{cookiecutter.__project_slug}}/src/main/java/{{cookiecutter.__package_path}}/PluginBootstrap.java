package {{ cookiecutter.package_name }};

import arc.util.Log;

public class PluginBootstrap {
    public PluginBootstrap() {
    }

    public void start() {
        Log.info("{{ cookiecutter.plugin_display_name }} bootstrap initialized.");
    }
}
