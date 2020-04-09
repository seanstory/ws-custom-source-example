package com.sstory.source.filesystem.api

import com.sstory.source.filesystem.sources.HelloWorldSource
import spock.lang.Specification

class SourcesUtilsTest extends Specification {

    def "test source lookup"(){
        when:
        def stream = new ByteArrayInputStream(text.getBytes("UTF-8"))
        def parser = new SourcesUtils(stream)

        then:
        parser.isEnabled(expectedContains)

        where:
        text             | expectedContains
        HelloWorldSource.getCanonicalName() | "helloworld"
        HelloWorldSource.getCanonicalName() | "helloWorld"
        HelloWorldSource.getCanonicalName() | "HelloWorldSource"
        HelloWorldSource.getCanonicalName() | "helloworldsource"
    }
}
