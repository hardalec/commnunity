package com.jing.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private long id;
    private String name;
    private String bio;
    private String avatarUrl;
}
