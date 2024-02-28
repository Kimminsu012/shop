package com.weapon.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weapon.shop.dto.ItemSearchDto;
import com.weapon.shop.dto.MainItemDto;
import com.weapon.shop.dto.QMainItemDto;
import com.weapon.shop.entity.QItem;
import com.weapon.shop.entity.QItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    // 위의 두가지는 querydsl 사용시 꼭 필요하다.

    @Override
    public Page<MainItemDto> getMainItem(ItemSearchDto itemSearchDto, Pageable pageable) {

        QItem item = QItem.item; // querydsl 작업을 위해 entity 객체 필요
        QItemImg itemImg = QItemImg.itemImg;

        List<MainItemDto> cont = jpaQueryFactory // 데이터베이스 조회해서 MainItemDto 객체에 저장하여 가져오기
                .select( // select 앞에 쓰는 컬럼만 가져온다.
                        new QMainItemDto( item.id, item.itemNm,
                                item.itemDetail, itemImg.imgUrl,
                                item.price)
                ) // sql문 - select item.id, item.item_nm, item.item_detail, item_img.img_url, item.price
                .from(itemImg) // from - 조회할 테이블 지정 하는 부분 / 2개 이상의 테이블을 조회 할 시 외래키가 있는 테이블을 지정
                .join(itemImg.item, item) // 외래키 - itemImg.item -> item.id , item 테이블 지정 / 주 - item 종 - itemImg / 테이블 관계에 따라 지정 되는 테이블이 다르다. join 은 주테이블 , from 은 종테이블
                .where(itemImg.repImgYn.eq("Y")) // item_img 테이블에서 대표이미지만 가져오라는 조건
                .where(itemNmLike(itemSearchDto.getSearchQuery())) // 검색어를 포함한 상품명 전부 조회
                .orderBy(item.id.desc()) // 상품번호기준으로 내림차순
                .offset(pageable.getOffset()) // 페이징 시에 시작번호 지정, 한페이지에 5개씩이라면 두번째 페이지 조회시 offset 은 5가된다.
                .limit(pageable.getPageSize()) // 한페이지에 표시할 데이터 갯수
                .fetch(); // 쿼리문 실행 / 다수의 데이터 조회시 fetch

        long total = jpaQueryFactory // 위에 만든 쿼리문의 총 갯수를 구하는 쿼리문
                .select(Wildcard.count) // select COUNT() -> 조회한 데이터 총갯수 구하는 함수
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .fetchOne(); // 단일 데이터 조회시 fetchOne

        return new PageImpl<>(cont,pageable,total); // content , 페이징 , total

    }

    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%"+searchQuery+"%");
    }

}


























