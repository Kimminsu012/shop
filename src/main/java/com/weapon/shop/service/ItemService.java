package com.weapon.shop.service;

import com.weapon.shop.constant.ItemSellStatus;
import com.weapon.shop.dto.ItemFormDto;
import com.weapon.shop.dto.ItemImgDto;
import com.weapon.shop.dto.ItemSearchDto;
import com.weapon.shop.dto.MainItemDto;
import com.weapon.shop.entity.Item;
import com.weapon.shop.entity.ItemImg;
import com.weapon.shop.repository.ItemImgRepository;
import com.weapon.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto,pageable);
    }

    // 아이템 상세보기
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgs = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for(ItemImg itemImg : itemImgs){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        Item item = itemRepository.findById(itemId).get();
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }


    // 메인페이지에 출력할 상품들 가져오기 ( 페이징 - 5개씩 보여주기 )
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItem(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItem(itemSearchDto,pageable);
    }

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> multipartFileList) throws Exception{
        // 상품 정보 데이터베이스에 저장
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 업로드 및 데이터베이스 저장
        for(int i = 0; i<multipartFileList.size(); i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item); // 이미지에 상품 번호(item_id)가 같이 저장된다.

            if(i==0) // 대표이지미 설정 - 무조건 첫번째 이미지를 대표이미지로 사용한다.
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");
            // 업로드및 데이터베이스저장 하기위해 service 클래스의 메서드 호출
            itemImgService.saveItemImg(itemImg, multipartFileList.get(i));
        }
        return item.getId();
    }





}
