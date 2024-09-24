package org.choongang.member.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestUpdate {
    private String email;

    @NotBlank
    private String userName;

    @Size(min=8)
    private String password;

    private String confirmPassword;

    private String mobile;

    private String fields;

    private String job;

    private String gender;

    private String authority;

    private List<String> belongings;

    private List<String> interests;
}