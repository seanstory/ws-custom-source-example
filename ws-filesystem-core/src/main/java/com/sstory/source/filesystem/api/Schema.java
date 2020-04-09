package com.sstory.source.filesystem.api;

import java.util.List;

public abstract class Schema {

    public static class Field {

        public static enum Type {
            Text,
            Number,
            Geolocation,
            Date
        }

        private final String name;
        private final Type type;

        public Field(String name, Type type){
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }

    }

    public abstract List<Field> getFields();

}
