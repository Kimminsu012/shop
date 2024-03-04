package com.weapon.shop.control;

import com.weapon.shop.dto.*;
import com.weapon.shop.entity.Cart;
import com.weapon.shop.entity.CartItem;
import com.weapon.shop.entity.Item;
import com.weapon.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public @ResponseBody ResponseEntity cartPut(@RequestBody @Valid CartItemDto cartItemDto,
                                                BindingResult bindingResult, Principal principal) {
        //장바구니 버튼클릭시

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder(); // json 응답은 문자열로 보내야 해서
            List<FieldError> fieldErrors = bindingResult.getFieldErrors(); // 유효성 검사 오류 내용

            for( FieldError fieldError : fieldErrors ){
                sb.append(fieldError.getDefaultMessage()); // json 응답을 보내기위해 StringBuilder 에 저장
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartItemId;
        try{
            cartItemId = cartService.addCart(cartItemDto, email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model) {
        //장바구니 메뉴 클릭시

        List<CartDetailDto> cartItems = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartItems);


        return "cart/cartList";
    }

    @PostMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity<Long> updateCartItem(@RequestBody CartItemDto cartItemDto, Principal principal) {
        // 상품 수량 변경

        cartService.updateCartItemCount(cartItemDto.getItemId(), cartItemDto.getCount());
        return new ResponseEntity( cartItemDto.getItemId(), HttpStatus.OK);
    }
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
        //장바구니 상품 삭제

        cartService.deleteCartItem(cartItemId);

        return  new ResponseEntity<Long>(cartItemId,HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal){
        // 장바구니 에서 상품 주문
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());

        return new ResponseEntity<Long>(orderId,HttpStatus.OK);

    }
}