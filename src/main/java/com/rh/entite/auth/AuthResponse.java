package com.rh.entite.auth;

import com.rh.model.Utilisateur;

public class AuthResponse {
    private boolean success;
    private String message;
    private Utilisateur user;

    public AuthResponse(boolean success, String message, Utilisateur user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
}