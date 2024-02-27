package com.weapon.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savefName = uuid.toString()+ext; // 새로만들어진 이름에 확장자 붙이기
        String fileUploadUrl = uploadPath+"/"+savefName;
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData); // 저장 위치에 저장
        fos.close();
        return savefName; // 실제 저장된 이름 반환
    }

    public void deleteFile(String filePath) throws Exception{

    }
}
