package com.sstory.source.filesystem.api;

import com.sstory.workplace.search.client.ClientKt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String ACCESS_TOKEN_CONFIG_KEY = "access_token";
    public static final String CONTENT_SOURCE_KEY_CONFIG_KEY = "content_source_key";
    public static final String ENDPOINT_CONFIG_KEY = "endpoint";
    public final String accessToken;
    public final String contentSourceKey;
    public final String endpoint;

    public YamlConfig(String yamlPath) throws FileNotFoundException {
        File file = new File(yamlPath);
        log.debug("Attempting to parse yaml config from: {}", file.getAbsolutePath());
        Yaml yaml = new Yaml();
        InputStream stream = new FileInputStream(file);
        Map<String, Object> config;
        try{
            config = yaml.load(stream);
        } catch (ClassCastException e){
            throw new IllegalArgumentException("The content of "+file.getAbsolutePath()+" was not of the expected format. This must be a YAML file");
        }

        if(config==null){
            throw new IllegalArgumentException("No yaml could be read from: "+file.getAbsolutePath());
        }

        this.accessToken = (String) config.get(ACCESS_TOKEN_CONFIG_KEY);
        this.contentSourceKey = (String) config.get(CONTENT_SOURCE_KEY_CONFIG_KEY);
        this.endpoint = config.containsKey(ENDPOINT_CONFIG_KEY) ? (String) config.get(ENDPOINT_CONFIG_KEY) : ClientKt.DEFAULT_ENDPOINT;
        if(this.accessToken == null || this.contentSourceKey == null){
            throw new ConfigurationException("Both "+ACCESS_TOKEN_CONFIG_KEY+" and "+CONTENT_SOURCE_KEY_CONFIG_KEY+" must be configured in "+yamlPath);
        }
        //TODO, extra validations?
    }

    public static class ConfigurationException extends RuntimeException{
        ConfigurationException(String msg){
            super(msg);
        }
    }
}
