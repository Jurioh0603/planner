package com.triplan.planner.plan.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class imageUploadForm {

    private long scheduleNo;
    private MultipartFile uploadFile;
}
