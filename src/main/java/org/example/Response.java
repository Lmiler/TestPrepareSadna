package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private boolean success;
    private String errorCode;
    private List<Task> tasks;

    public boolean getSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
