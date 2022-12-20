package com.charles.website.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity @AllArgsConstructor @NoArgsConstructor
public class Category extends BaseDomain{
    private String name;
}
