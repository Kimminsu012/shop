package com.weapon.shop.control;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CartController {

    @PostMapping("/cart")
    public @ResponseBody ResponseEntity cartPut(){

        return new ResponseEntity<Long>(1L, HttpStatus.OK);
    }
}
