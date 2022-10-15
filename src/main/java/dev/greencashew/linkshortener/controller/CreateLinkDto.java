package dev.greencashew.linkshortener.controller;

import dev.greencashew.linkshortener.dto.LinkDto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

record CreateLinkDto(
        @Schema(description = "Indentifier/alias to link. Used for redirection.", example = "link-alias", required = true)
        @NotBlank @Size(min =1, max = 60)
        String id,
        @NotBlank @Email
        String email,
        @NotBlank
        String targetUrl,
        @Future
        LocalDate expirationDate) {

    LinkDto toDto() {
        return new LinkDto(
                id,
                email,
                targetUrl,
                expirationDate,
                0
        );
    }
}
