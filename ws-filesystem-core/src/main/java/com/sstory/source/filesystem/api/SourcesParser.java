package com.sstory.source.filesystem.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class SourcesParser {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final Map<String, Class<? extends Source<? extends DocumentBase>>> enabledSources;

    public SourcesParser() throws IOException, ClassNotFoundException, ClassCastException {
        this(SourcesParser.class.getClassLoader().getResourceAsStream("sources"));
    }

    private SourcesParser(InputStream stream) throws IOException, ClassNotFoundException, ClassCastException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Map<String, Class<? extends Source<? extends DocumentBase>>> sources = new HashMap<>();
        while(reader.ready()) {
            String line = reader.readLine().trim();
            try {
                Class<? extends Source<? extends DocumentBase>> klass = (Class<? extends Source<? extends DocumentBase>>) Class.forName(line);
                sources.put(klass.getSimpleName().toLowerCase(), klass);
            } catch (ClassNotFoundException | ClassCastException e) {
                log.error("Failed to find class with name {} from the 'sources' resource", line);
                throw e;
            }

        }
        this.enabledSources = sources;
    }

    public Set<String> getEnabledSources(){
        return this.enabledSources.keySet();
    }

    public boolean isEnabled(String sourceName){
        String lowerSourceName = sourceName.trim().toLowerCase();
        String withSourceName = lowerSourceName.endsWith("source") ? lowerSourceName : lowerSourceName+"source";
        return getEnabledSources().contains(lowerSourceName) || getEnabledSources().contains(withSourceName);
    }

    public Class<? extends Source<? extends DocumentBase>> getSource(String sourceName){
        if (!isEnabled(sourceName)){
            return null;
        } else {
            String lowerSourceName = sourceName.trim().toLowerCase();
            String withSourceName = lowerSourceName.endsWith("source") ? lowerSourceName : lowerSourceName+"source";
            if(enabledSources.containsKey(lowerSourceName)){
                return enabledSources.get(lowerSourceName);
            } else {
                return enabledSources.get(withSourceName);
            }
        }
    }
}
