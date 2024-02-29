package com.weapon.shop.dto;

import com.weapon.shop.constant.OrderStatus;
import com.weapon.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter  @Setter
public class OrderHistDto {

    private Long orderId; // 주문 번호
    private String orderDate; // 주문 날짜
    private OrderStatus orderStatus; // 주문 상태

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHistDto(Order order){
        this.orderId=order.getId();
        this.orderDate=order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.orderStatus=order.getOrderStatus();
    }
    public void addOrderItemDto(OrderItemDto orderItemDto){ // 주문 상품 목록
        orderItemDtoList.add(orderItemDto);
    }
}
