package com.sstory.source.filesystem

import spock.lang.Specification

class DemoClassTest extends Specification {

    def "test that it works"(){
        setup:
        def stu = new DemoClass("foo")

        when:
        def name = stu.name

        then:
        name == "foo"
    }
}
