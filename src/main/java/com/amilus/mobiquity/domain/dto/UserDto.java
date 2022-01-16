package com.amilus.mobiquity.domain.dto;

import lombok.Data;

@Data
public class UserDto
{
    private Integer id;
    private String name;
    private String username;
    private String email;
    private AddressDto address;
    private String phone;
    private String website;
    private CompanyDto company;
}
