package org.choongang.member.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestJoin {

    private String gid = UUID.randomUUID().toString();

    @NotBlank
    @Email
    private String email;

    @NotBlank @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String userName;

    @NotBlank
    private String mobile;

    @NotNull
    @Past
    private LocalDate birth;

    private String gender;

    private List<String> belongings;


    private List<String> interests;

    @AssertTrue
    private boolean agree;
}