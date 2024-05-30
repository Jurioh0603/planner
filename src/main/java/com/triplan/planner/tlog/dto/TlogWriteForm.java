package com.triplan.planner.tlog.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class TlogWriteForm {

    private String title;
    private String content;
    private List<MultipartFile> file;
    private Long scheduleNo;
}
