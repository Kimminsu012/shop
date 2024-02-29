package com.weapon.shop.dto;

import com.weapon.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class OrderItemDto {

    private String itemNm; // 상품명
    private int count; // 수량
    private int orderPrice; // 주문 금액
    private String imgUrl; // 상품 대표 이미지경로

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.count=orderItem.getCount();
        this.itemNm=orderItem.getItem().getItemNm();
        this.imgUrl=imgUrl;
        this.orderPrice=orderItem.getOrderPrice();

    }
}
