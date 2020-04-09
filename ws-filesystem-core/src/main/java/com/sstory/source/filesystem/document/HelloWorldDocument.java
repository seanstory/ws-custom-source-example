package com.sstory.source.filesystem.document;

import com.sstory.source.filesystem.api.DocumentBase;

import java.util.Collections;
import java.util.Map;

public class HelloWorldDocument extends DocumentBase {

    public HelloWorldDocument(String id) {
        super(id, "hello, world!");
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Collections.emptyMap();
    }
}
