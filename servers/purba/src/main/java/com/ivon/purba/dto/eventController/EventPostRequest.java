package com.ivon.purba.dto.eventController;

import com.ivon.purba.domain.ContentType;
import com.ivon.purba.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventPostRequest {
    private Long userId;
    private String eventTypeName;
    private String location;
    private String title;
    private String data;
    private String photoUrl;
    private Date startDate;
    private Date endDate;
    private Integer charge;
    private String backAccount;
    private String summary;
}
