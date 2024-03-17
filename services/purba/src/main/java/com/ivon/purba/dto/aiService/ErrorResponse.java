package com.ivon.purba.dto.aiService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String type;
    private String param;
    private String code;
}
