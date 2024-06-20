package com.triplan.planner.file;

import lombok.Data;

@Data
public class UploadFile {

    //사용자가 업로드한 파일 이름
    private String uploadFileName;
    //UUID로 변환되어 저장된 파일 이름
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
