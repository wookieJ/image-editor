package pl.put.plugin

import spock.lang.Specification
import spock.lang.Unroll

class PluginLoaderTest extends Specification {
    @Unroll
    def "loadPlugins(#pluginsDirectory, #configFileName).length == #result"() {
        expect:
        PluginLoader.loadPlugins(pluginsDirectory, configFileName).length == result

        where:
        pluginsDirectory | configFileName | result
        "plugins"        | "config.cfg"   | 1
    }

    @Unroll
    def "initAsPlugin(loadPlugins(#pluginsDirectory, #configFileName)).length == #result"() {
        expect:
        PluginLoader.initAsPlugin(PluginLoader.loadPlugins(pluginsDirectory, configFileName)).length == result

        where:
        pluginsDirectory | configFileName | result
        "plugins"        | "config.cfg"   | 1
    }
}