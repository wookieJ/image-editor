package pl.put.propertyUtils

import spock.lang.Specification
import spock.lang.Unroll

class PropertyServiceTest extends Specification {
    @Unroll
    def "loadFromFile(#filePath).isEmpty() == #result"() {
        expect:
        PropertyService propertyService = new PropertyService()
        propertyService.loadFromFile(filePath) != null
        propertyService.loadFromFile(filePath).isEmpty() == result

        where:
        filePath                         | result
        "properties\\default.properties" | false
    }
}
