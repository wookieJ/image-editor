package pl.put.plugin

import pl.put.Main
import spock.lang.Specification
import spock.lang.Unroll

class PluginLoaderTest extends Specification {
    @Unroll
    def "max(#a,#b) == #c"() {
        expect:
        Main.max(a, b) == c

        where:
        a  | b   | c
        1  | 2   | 2
        42 | -12 | 42
    }
}