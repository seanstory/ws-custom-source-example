package com.sstory.source.filesystem.schema;

import com.sstory.source.filesystem.api.DocumentBase;
import com.sstory.source.filesystem.api.Schema;

import java.util.List;

public class HelloWorldSchema extends Schema {

    @Override
    public List<Field> getFields() {
        return DocumentBase.getDefaultFields();
    }
}
