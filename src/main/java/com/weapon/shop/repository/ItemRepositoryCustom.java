package com.weapon.shop.repository;

import com.weapon.shop.dto.ItemSearchDto;
import com.weapon.shop.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<MainItemDto> getMainItem(ItemSearchDto itemSearchDto, Pageable pageable);
}
