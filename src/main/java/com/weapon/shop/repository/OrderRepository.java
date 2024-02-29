package com.weapon.shop.repository;

import com.weapon.shop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    // 현재 로그인한 회원의 구매 이력만 가져오기 위한 내용
    @Query("select o from Order o where o.member.email = :email order by o.orderDate desc")
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    // 현재 로그인 한 회원의 구매상품 총 갯수 가져오기 - 페이징을 위한
    @Query("select count(o) from Order o where o.member.email = :email")
    Long countOrder(@Param("email") String email);

}
