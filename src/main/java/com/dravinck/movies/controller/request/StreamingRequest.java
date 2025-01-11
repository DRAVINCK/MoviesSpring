package com.dravinck.movies.controller.request;

import jakarta.validation.constraints.NotEmpty;

public record StreamingRequest(@NotEmpty(message = "Nome do streaming é obrigatório.")
                               String name) {
}
