package com.weapon.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weapon.shop.dto.ItemSearchDto;
import com.weapon.shop.dto.MainItemDto;
import com.weapon.shop.dto.QMainItemDto;
import com.weapon.shop.entity.QItem;
import com.weapon.shop.entity.QItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

//        List<MainItemDto> cont = jpaQueryFactory
//                .select( new QMainItemDto( item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price))
//                .from(itemImg)
//                .join(itemImg.item, item)
//                .where(itemImg.repImgYn.eq("Y"))
//                .where()
//
                return null;
    }
}
