package com.example.tubespboo.exception;

public class DuplicateResource extends RuntimeException {
    public DuplicateResource(String message) {
        super(message);
    }
}