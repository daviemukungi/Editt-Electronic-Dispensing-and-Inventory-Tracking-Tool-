package org.msh.fdt.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import static com.fasterxml.jackson.annotation.JsonInclude.*;

public class CustomJacksonObjectMapper extends ObjectMapper {

    public CustomJacksonObjectMapper() {
        super();
        this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        this.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        this.setSerializationInclusion(Include.NON_NULL);
    }
}
