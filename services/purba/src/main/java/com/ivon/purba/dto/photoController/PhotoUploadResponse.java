package com.ivon.purba.dto.photoController;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PhotoUploadResponse {
    private String contentTypeName;
    private String title;
    private String summary;
    private String location;
    private String bankAccount;
    private Integer charge;
    private Date startDate;
    private Date endDate;

    public PhotoUploadResponse(String contentTypeName, String title, String summary, String location, Date startDate, Date endDate, Integer charge, String bankAccount) {
        this.contentTypeName = contentTypeName;
        this.title = title;
        this.summary = summary;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.charge = charge;
        this.bankAccount = bankAccount;
    }
}
