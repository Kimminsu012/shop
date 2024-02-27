package com.weapon.shop.entity;

import com.weapon.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@ToString
public class Item extends BaseEntity{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품이름

    @Column(nullable = false)
    private int price; // 상품가격

    @Column(nullable = false)
    private Integer stockNumber; // 재고수량

    @Type(type = "org.hibernate.type.TextType")
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 가능 상태

}

/*
    @Entity - 클래스를 엔티티로 선언 하겠다.(데이터베이스 테이블 매핑)
    @Table - 엔티티와 매핑할 테이블 이름 설정 @Table(name="item_s")

    @Id - 테이블의 기본키(primary key) 지정
    @GeneratedValue - 기본키의 값을 생성하는 방법 명시
                      GenerationType.IDENTITY : 기본키 생성 (기본값 생성)
                      GenerationType.AUTO     : 자동증가
                      GenerationType.SEQUENCE : 데이터베이스 시퀸스 오브젝트를 이용한 값생성

    @Column - 필드와 테이블의 컬럼 매핑
              @Column(속성 = )
              name : 필드와 매핑할 컬럼의 이름 설정
              unique : 유니크 제약 조건 설정 (중복 X)
              insertable : insert 가능 여부 (true , false)
              updateable : update 가능 여부 (true , false)
              nullable : null 값 허용 여부 (true , false) 기본값 : true
              columnDefinition : 데이터베이스 컬럼 정보 직접 기술
                        @Column(columnDefinition = "name varchar(20) not null")
              pecision , scale : BigDecimal 타입에서 사용, percision 은 소수점 포함 전체 자리수,
                                 scale 은 소수점 자리수

    @Lob - BLOB , CLOB 타입 매핑, 대용량 데이터 저장 가능
           BLOB : 이진데이터를 저장 , 이미지나 파일 저장
           CLOB : 문자열이나 텍스트 데이터 저장
    @CreationTimestamp - insert 시 시간 작동 저장
    @UpdateTimestamp - update 시 시간 자동 저장
    @Enumerated - enum 타입 매핑
    @Transient - 해당 필드 데이터베이스 매핑 무시
    @Temporal - 날짜 타입 매핑
    @CreateDate - 엔티티가 생성되어 저장될때 시간 자동 저장
    @LastModifiedDate - 조회한 엔티티의 값을 변경 할때 시간 자동 저장

 */











