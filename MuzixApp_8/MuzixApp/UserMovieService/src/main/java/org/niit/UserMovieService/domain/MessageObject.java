package org.niit.UserMovieService.domain;

import java.util.List;

public class MessageObject {
    private String message;

    public MessageObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
