package com.weapon.shop.repository;

import com.weapon.shop.dto.ItemSearchDto;
import com.weapon.shop.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    // 상품 이름으로 조회
    List<Item> findByItemNm(String itemNm);

    // 상품명, 상품상세설명으로 검색
    List<Item> findByItemNmOrItemDetail(String itemNm, String ItemDetail);

    // 상품가격이 30000이하인 상품 조회
    List<Item> findByPriceLessThanEqualOrderByPriceDesc(Integer price);

    // 상품가격이 15000원에서 35000원 사이의 상품들만 조회
    List<Item> findByPriceBetweenOrderByPriceDesc(Integer downPrice, Integer upPrice);

    // findByItemDetailLikeOrderByPriceDesc(String itemDetail)
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc") // Entity 에 있는 값으로 한다.
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value="select * from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true) // 실제 Query 문
    List<Item>  findByItemDetailNative(@Param("itemDetail") String itemDetail);

}
/*

    JPA 와 JPQL 의 단점은 에러 발견이 안된다.
    문자열로 쿼리를 입력하기 때문에 잘못입력하여도 컴파일 시점에서는 에러를 발견할 수 없다.
    그래서 단점을 보완 하고자 만들어진 녀석이 Querydsl 이다.

    Querydsl 의 장점
    - 고정된 SQL 문이 아닌 조건에 맞게 동적으로 쿼리를 생성 할 수 있다.
    - 비슷한 쿼리를 재사용할 수 있으며 제약 조건 조립 및 가독성을 향상 시킬 수 있다.
    - 문자열이 아닌 자바 소스코드로 작성하기 때문에 컴파일 시점에 오류를 발견 할 수 있다.
    - IDE 의 도움으로 자동완성 기능 사용해서 생산성 향상 시킬 수 있다.




    @Query 에너테이션을 이용하여 sql 과 유사한 JPQL 이라는 객체지향 쿼리 언어를 통해
    복잡한 쿼리도 처리가 가능하다.
    파라미터(:itemDetail) 에 @Param 에너테이션을 이용하여 파라미터로 넘어온 값을 JPQL 에
    들어갈 변수로 지정할 수 있다.
     : 는 변수앞에 붙여서 사용한다.
    @Query("select * from Item where price < :price")
    List<Item> priceThan(@Param("price") Integer price);
 */

// jpa 의 네이밍 규칙을 이용하여 메서드를 작성하면 원하는 쿼리를 실행 할 수 있다.
// 데이터베이스에서 조회할 메서드 규칙 - find + By + 변수이름
//          findByItemNm - 상품 이름으로 조히 하는 메서드
/*
    keyword             sample                          query

    And                 findByItemNmAndPrice            where item_nm='a' and price=1000 (둘 다 충족)
    Or                  findByItemOrPrice               where item='a' or price=1000 (둘중 하나 충족)
    Is, Equals          findByItemNm
                        findByItemNmIs                  where item_nm='a'   (3개의 sample 의 query)
                        findByItemNmEquals
    Between             findByRegDateBetween            where reg_date between 2024-01-01 and 2024-02-03 (범위 검색)
    LessThan            findByPriceLessThan             where price < 50000 (작은 값(미만) 검색)
    LessThanEqual       findByPriceLessThanEqual        where price <= 50000 (작거나 같은 값(이하) 검색)
    GreaterThan         findByPriceGreaterThan          where price > 30000 (큰 값(초과) 검색)
    GreaterThanEqual    findByPriceGreaterThanEqual     where price >= 30000 (크거나 같은 값(이상) 검색)
    After               findByRegDateAfter              where reg_date > 2024-01-01 (이후 날짜)
    Before              findByRegDateBefore             where reg_date < 2024-02-03 (이전 날짜)
    IsNull , Null       findByItemNmNull                where item_nm is null (null 값 찾기)
    NotNull             findByItemNmNotNull             where item_nm is not null (null 이 아닌 값 찾기)
    Like                findByItemNmLike                where item_nm like '가방' (포함)
    StartingWith        findByItemNmStartingWith        where item_nm like '가방%' (시작하는 값)
    EndingWith          findByItemNmEndingWith          where item_nm like '%가방' (끝나는 값)
                        findByItemNmStartingWithIgnoreCase (영어인 값을 찾을 시 대소문자 구별 안하고 할때)
    OrderBy             findByPriceOrderByRegDateDesc   where price=3000 order by reg_date desc (내림차순으로 가져올때)
                        findByPriceOrderByRegDateAsc   where price=3000 order by reg_date asc (오름차순으로 가져올때)



 */
