package com.sstory.source.filesystem.run;

import com.sstory.source.filesystem.api.DocumentBase;
import com.sstory.source.filesystem.api.Source;
import com.sstory.source.filesystem.api.SourcesParser;
import com.sstory.source.filesystem.api.YamlConfig;
import com.sstory.workplace.search.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//TODO test this
public class Sync {

    private static Logger log = LoggerFactory.getLogger(Sync.class);

    public static void main(String[] args){
        String yamlPath = args[0];
        YamlConfig yamlConfig;
        try {
             yamlConfig = new YamlConfig(yamlPath);
        } catch (Exception e) {
            log.error("Failed to load configuration: {}: {}", e.getClass().getSimpleName(), e.getMessage());
            log.debug("with stack trace: ", e); //TODO, refactor duplicate error handling
            System.exit(1);
            return; //because the compiler doesn't realize that the System.exit call is terminating.
        }
        Client client = new Client(yamlConfig.accessToken, yamlConfig.endpoint);
        Source source = null;//TODO, dependency inject? Not sure I like this. Maybe from a resource file?
        try {
            SourcesParser parser = new SourcesParser();
            String sourceName = getSourceName(args, parser);
            if (parser.isEnabled(sourceName)){
                source = parser.getSource(sourceName).newInstance(); //TODO constructor args?
            } else {
                log.error("{} is not one of the enabled sources: {}", sourceName, parser.getEnabledSources());
            }

        } catch (ClassNotFoundException | ClassCastException e) {
            log.error("Could not find a proper Source class with name: {}", args[1]);
            log.debug("with stack trace: ", e);
            System.exit(1);
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("Failed to load class: {}", args[1]);
            log.debug("with stack trace: ", e);
            System.exit(1);
        } catch (IOException e) {
            log.error("Failed to load sources resource");
            log.debug("with stack trace: ", e);
            System.exit(1);
        }

        Iterator<? extends DocumentBase> documents = source.getDocuments();

        //batches of 100
        int count = 0;
        while (documents.hasNext()){
            List<Map<String, Object>> batch = new ArrayList<>();
            for(int i = 0; i<100; i++){
                batch.add(documents.next().toMap());
                if(!documents.hasNext()){
                    break;
                }
            }
            try {
                client.indexDocuments(yamlConfig.contentSourceKey, batch);
                count += batch.size();
                log.info("Successfully indexed a batch of {} documents.{}", batch.size(), documents.hasNext() ? " "+count+" total, so far" : "");
            } catch (Client.WorkplaceSearchClientException e) {
                log.error("Failed to successfully index to Workplace Search because of: {}: {}", e.getClass().getSimpleName(), e.getMessage());
                log.debug("with stack trace: ", e);
                System.exit(1);
            }
        }

        log.info("Finished indexing {} documents", count);
        System.exit(0);
    }

    private static String getSourceName(String[] args, SourcesParser parser){
        if(args.length >=2){
            return args[1];
        } else {
            log.error("You must provide a source name to sync. Eligible source names are: {}", parser.getEnabledSources());
            System.exit(1);
            return null; //Not reached.
        }
    }
}
