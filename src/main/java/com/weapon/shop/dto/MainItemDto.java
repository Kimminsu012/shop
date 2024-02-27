package com.weapon.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class MainItemDto {

    private Long id; // 상품번호
    private String itemNm; // 상품명
    private Integer price; // 상풍 가격
    private String imgUrl; // 상품 대표 이미지
    private String itemDetail; // 상품 상세 내용

    // querydsl 을 통해서 조회한 데이터를 MainItemDto 객체로 저장해서 가져오기 위한 내용
    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){
        this.id=id;
        this.itemNm=itemNm;
        this.itemDetail=itemDetail;
        this.imgUrl=imgUrl;
        this.price=price;

    }
}
