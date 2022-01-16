package com.amilus.mobiquity.domain.dto;

import lombok.Data;

@Data
public class AddressDto
{
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoDto geo;
}
