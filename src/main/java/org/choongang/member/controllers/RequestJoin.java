package org.choongang.member.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.choongang.member.constants.Belonging;
import org.choongang.member.constants.Gender;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private List<Belonging> belongings;

    private String interests;

    @AssertTrue
    private boolean agree;
}