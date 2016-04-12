package org.msh.fdt.util;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kenny on 4/10/14.
 */
public class JTableResponse {

    private String result;
    private Object record;
    private String message;

    public JTableResponse(){}

    public JTableResponse(String result, String message) {
        setResult(result);
        setMessage(message);
    }

    @JsonProperty("Result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("Record")
    public Object getRecord() {
        return record;
    }

    public void setRecord(Object record) {
        this.record = record;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
