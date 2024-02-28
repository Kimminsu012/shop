package com.weapon.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter  @Setter
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column
    private int orderPrice; // 주문 가격
    @Column
    private int count; // 주문 수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(item.getPrice());
        orderItem.setCount(count);
        item.removeStock(count); // 주문수량 만큼 상품 수량 감소

        return orderItem;
    }

    public int getTotalPrice(){ // 총 결제 금액
        return orderPrice*count;
    }

    public void cancel(){ // 주문취소 - 수량 원복
        this.getItem().addStock(count);
    }

}
