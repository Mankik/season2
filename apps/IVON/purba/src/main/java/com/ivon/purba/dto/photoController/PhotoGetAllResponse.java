package com.ivon.purba.dto.photoController;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class PhotoGetAllResponse {
    private MultipartFile file;
}
