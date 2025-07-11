package com.example.webapplication.Exception;

public class InventoryOverloadedException extends RuntimeException {
    public InventoryOverloadedException(String message) {
        super(message);
    }
}
