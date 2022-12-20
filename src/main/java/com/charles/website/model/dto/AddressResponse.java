package com.charles.website.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddressResponse {
    private String city;
    private String district;
    private String ward;
    private String house;
}
