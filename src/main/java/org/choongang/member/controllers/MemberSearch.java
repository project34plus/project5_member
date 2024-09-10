package org.choongang.member.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;

@Data
public class MemberSearch extends CommonSearch {
    private String email;
    private String userName;
}