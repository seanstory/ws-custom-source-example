package com.sstory.source.filesystem.sources;

import com.sstory.source.filesystem.api.Source;
import com.sstory.source.filesystem.document.HelloWorldDocument;

import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;

public class HelloWorldSource implements Source<HelloWorldDocument> {

    private final String greeting;

    public HelloWorldSource(){
        greeting = "hello, world!";
    }

    public HelloWorldSource(String greeting){
        this.greeting = greeting;
    }

    @Override
    public Iterator<HelloWorldDocument> getDocuments() {
        return Collections.singletonList(new HelloWorldDocument(String.valueOf(Instant.now().toEpochMilli()), greeting)).iterator();
    }
}
