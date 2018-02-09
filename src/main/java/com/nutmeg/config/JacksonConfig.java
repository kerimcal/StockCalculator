package com.nutmeg.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class JacksonConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonConfig.class);

    /*@Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(){
        return Jackson2ObjectMapperBuilder.json()
                .modules(new JodaModule(), new GuavaModule())
                .failOnUnknownProperties(false)
                .serializationInclusion(JsonInclude.Include.NON_ABSENT)
                .featuresToDisable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }*/

    public static <T> T readResourceToType(InputStream inputStream, Class<T> targetType) throws JsonParseException, JsonMappingException, IOException {
        return OBJECT_MAPPER.readValue(inputStream, targetType);
    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.INDENT_OUTPUT, true);

    public static String prettyFormat(Object map) {
        try {
            return OBJECT_MAPPER.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON transformation exception", e);
            return null;
        }
    }

    /*public static Document mapObjectToDocument(final Object object){
        try {
            final String rawString = OBJECT_MAPPER.writeValueAsString(object);
            return OBJECT_MAPPER.readValue(rawString, Document.class);
        }catch(IOException ex){}
        return null;
    }*/

    public static String mapObjectToString(final Object object) {
        try {
            final String string = OBJECT_MAPPER.writeValueAsString(object);
            return string;
        } catch(Exception ex){
            throw new IllegalArgumentException(ex.getMessage(),ex);
        }
    }

}
