package com.example.meualmoxarifadobackend.service.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException(String resource) {
        super("%s não encontrada(o).".formatted(resource));
    }
}
