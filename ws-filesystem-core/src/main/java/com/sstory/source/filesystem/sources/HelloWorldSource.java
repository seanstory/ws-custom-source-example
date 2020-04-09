package com.sstory.source.filesystem.sources;

import com.sstory.source.filesystem.api.Source;
import com.sstory.source.filesystem.document.HelloWorldDocument;

import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;

public class HelloWorldSource implements Source<HelloWorldDocument> {

    @Override
    public Iterator<HelloWorldDocument> getDocuments() {
        return Collections.singletonList(new HelloWorldDocument(String.valueOf(Instant.now().toEpochMilli()))).iterator();
    }
}
