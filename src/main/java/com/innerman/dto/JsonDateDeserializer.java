package com.innerman.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by petrpopov on 27/06/16.
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    private SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String dt = jp.getText();
        if (dt == null || dt.trim().length() == 0) {
            return null;
        }

        Date date = null;
        try {
            date = fmt.parse(dt);
        } catch (Exception e) {
            throw new IOException(e);
        }

        return date;
    }
}
