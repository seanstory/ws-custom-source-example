package com.seanjstory.source.custom.example.document;

import com.seanjstory.workplace.search.sdk.api.DocumentBase;

import java.util.Collections;
import java.util.Map;

public class HelloWorldDocument extends DocumentBase {

    public HelloWorldDocument(String id) {
        super(id, "hello, world!");
    }

    public HelloWorldDocument(String id, String body){
        super(id, body);
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Collections.emptyMap();
    }
}
