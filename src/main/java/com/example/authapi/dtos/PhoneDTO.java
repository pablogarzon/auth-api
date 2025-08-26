package com.example.authapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private Long number;
    private Integer citycode;
    private String contrycode;
}
