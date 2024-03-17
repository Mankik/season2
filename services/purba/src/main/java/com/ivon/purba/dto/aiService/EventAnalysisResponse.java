package com.ivon.purba.dto.aiService;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventAnalysisResponse {
    private String summary;
    private Date startDate;
    private Date endDate;
    private Integer charge;
    private String bankAccount;
}
