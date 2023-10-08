package com.example.fc_drug.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    @JsonProperty("address-name")
    private String addressName;

    @JsonProperty("y")
    private double latitude;

    @JsonProperty("x")
    private double longitude;


}
