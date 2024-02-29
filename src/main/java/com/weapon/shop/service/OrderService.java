package com.weapon.shop.service;

import com.weapon.shop.dto.OrderDto;
import com.weapon.shop.dto.OrderHistDto;
import com.weapon.shop.dto.OrderItemDto;
import com.weapon.shop.entity.*;
import com.weapon.shop.repository.ItemImgRepository;
import com.weapon.shop.repository.ItemRepository;
import com.weapon.shop.repository.MemberRepository;
import com.weapon.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    //
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).get();

        order.cancelOrder();
    }


    // 구매하기 버튼 클릭시 구매 이력으로 저장
    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId()).get(); // 구매 상품 객체

        Member member = memberRepository.findByEmail(email); // 구매자

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();

    }

    // 구매이력 메뉴 클릭시 로그인한 회원의 구매이력 가져오기
    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long total = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for(Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);

            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem : orderItems){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<>(orderHistDtos, pageable, total);
        // 페이징 - new PageImpl<>( List<> 객체 , Pageable 객체 , 총 데이터갯수 );
    }


    // 구매 취소
}
