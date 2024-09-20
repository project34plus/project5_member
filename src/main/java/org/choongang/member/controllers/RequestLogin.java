package org.choongang.member.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestLogin {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private boolean success = true;
    private String code;
    private String defaultMessage;
    private String redirectUrl;
}
