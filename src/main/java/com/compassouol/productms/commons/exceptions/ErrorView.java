package com.compassouol.productms.commons.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorView {

    @JsonProperty("status_code")
    private Integer statusCode;

    private String message;

    public void addError(Integer statusCode, String field, String message) {
        this.message = field + " " + message;
        this.statusCode = statusCode;
    }

}
