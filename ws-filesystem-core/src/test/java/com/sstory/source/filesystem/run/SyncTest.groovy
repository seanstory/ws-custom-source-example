package com.sstory.source.filesystem.run

import org.junit.Rule
import org.junit.contrib.java.lang.system.ExpectedSystemExit
import org.junit.contrib.java.lang.system.internal.CheckExitCalled
import spock.lang.Ignore
import spock.lang.Specification

@Ignore //TODO - mock out network/client stuff so that this can be run
class SyncTest extends Specification {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    def "test the sync"(){
        setup:
        def yamlPath = "src/test/resources/test.yml"
        def sourceName = "helloworld"

        when:
        exit.expectSystemExit()
        Sync.main(yamlPath, sourceName)

        then:
        CheckExitCalled e = thrown()
        e.status == 0
    }
}
