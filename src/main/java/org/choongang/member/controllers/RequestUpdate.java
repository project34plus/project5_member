package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.choongang.member.constants.Belonging;
import org.choongang.thisis.entities.Interests;

import java.util.List;

@Data
public class RequestUpdate {
    private String email;

    @NotBlank
    private String userName;

    private String password;

    private String confirmPassword;

    private String mobile;

    private List<String> authority;

    private List<Belonging> belongings;

    private List<Interests> interests;
}