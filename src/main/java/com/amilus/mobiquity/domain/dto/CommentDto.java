package com.amilus.mobiquity.domain.dto;

import lombok.Data;

@Data
public class CommentDto
{
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
