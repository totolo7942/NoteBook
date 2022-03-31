

import spock.lang.*


class SimpleSpockTest extends Specification{

    def "one plus one should equal two"() {
        given:
        def list = [1, 2, 3, 4]

        when:
        list.remove(0)

        then:
        list == [2, 3, 4]
    }
}
