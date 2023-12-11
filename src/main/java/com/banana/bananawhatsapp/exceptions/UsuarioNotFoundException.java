package com.banana.bananawhatsapp.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
