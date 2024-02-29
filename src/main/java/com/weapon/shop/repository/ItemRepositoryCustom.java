package com.weapon.shop.repository;

import com.weapon.shop.dto.ItemSearchDto;
import com.weapon.shop.dto.MainItemDto;
import com.weapon.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<MainItemDto> getMainItem(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
