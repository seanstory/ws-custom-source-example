package com.sstory.source.filesystem.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//TODO add some validations? Like against a schema, and the list of allowed props?
public abstract class DocumentBase{

    private static final List<Schema.Field> DEFAULT_FIELDS = new ArrayList<>();
    public static final List<Schema.Field> getDefaultFields(){
        if (DEFAULT_FIELDS.isEmpty()){
            synchronized (DEFAULT_FIELDS) {
                if(DEFAULT_FIELDS.isEmpty()) { //you'd think this is redundant, but it's not /shrug
                    DEFAULT_FIELDS.add(new Schema.Field("id", Schema.Field.Type.Text));
                    DEFAULT_FIELDS.add(new Schema.Field("body", Schema.Field.Type.Text));
                }
            }
        }
        return DEFAULT_FIELDS;
    }

    private final String id;
    private final String body;

    public DocumentBase(String id, String body){
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public abstract Map<String, Object> getMetadata();

    public Map<String, Object> toMap(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", getId());
        map.put("body", getBody());
        for(Map.Entry<String, Object> metaEntry: getMetadata().entrySet()){
            map.put(metaEntry.getKey(), metaEntry.getValue());
        }
        return map;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof DocumentBase){
            return getId().equals(((DocumentBase)obj).getId());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return getId().hashCode();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + "(id: '" + getId() + "', body: '" + getBody() + "')";
    }
}
