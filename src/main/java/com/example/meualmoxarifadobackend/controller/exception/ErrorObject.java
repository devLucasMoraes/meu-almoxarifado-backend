package com.example.meualmoxarifadobackend.controller.exception;

public record ErrorObject(String message, String field, Object parameter) {
}
