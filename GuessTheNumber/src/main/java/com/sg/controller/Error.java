package com.sg.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Error {
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    private String message;

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
