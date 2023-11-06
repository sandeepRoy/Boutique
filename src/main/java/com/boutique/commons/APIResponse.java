package com.boutique.commons;

import java.time.LocalDate;

public class APIResponse {
    private Boolean success;
    private String message;


    public APIResponse() { }

    public APIResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }


    public String getMessage() {
        return message;
    }

    public String getTimeStamp(){
        return LocalDate.now().toString();
    }
}
