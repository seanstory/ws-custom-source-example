package com.sstory.source.filesystem.api;

import java.util.Iterator;

public interface Source<T extends DocumentBase> {

    public Iterator<T> getDocuments();

}
