package com.weapon.shop.service;

import com.weapon.shop.entity.ItemImg;
import com.weapon.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String imgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile multipartFile) throws Exception{
        String oriName = multipartFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드 부분
        if( !StringUtils.isEmpty(oriName) ){ // 사용자가 업로드 한 원본 이미지이름 여부 (false = 있음 , true = 없음)
            imgName = fileService.uploadFile(imgLocation,oriName, multipartFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }
        // 데이터베이스에 저장할때는 entity 객체로 저장 한다.
        itemImg.setImgUrl(imgUrl);
        itemImg.setImgName(imgName);
        itemImg.setOriImgName(oriName);
        // 레포지스토리에 저장
        itemImgRepository.save(itemImg);
    }








}












