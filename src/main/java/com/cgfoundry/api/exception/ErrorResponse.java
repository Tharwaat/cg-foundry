package com.cgfoundry.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private final String type;
    private final String title;
    private final int status;
    private final String detail;
    private final String instance;
    private final String timestamp;

}
