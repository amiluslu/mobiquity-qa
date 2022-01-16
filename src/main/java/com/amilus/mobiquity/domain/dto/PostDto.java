package com.amilus.mobiquity.domain.dto;

import lombok.Data;

@Data
public class PostDto
{
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
