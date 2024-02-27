package com.weapon.shop.control;

import com.weapon.shop.dto.MemberFormDto;
import com.weapon.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members") // 주소에서 입력한 값을 제외한 주소만 입력해도 된다. 공통으로 들어가는 주소에 대한 처리
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model){

        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/memberForm";
    }

    @PostMapping("/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bind, Model model){

        if(bind.hasErrors()){ // true 라면 유효성검사에 문제 발생
            return "member/memberForm";
        }

        try{
            memberService.saveMember(memberFormDto, passwordEncoder);

        }catch (IllegalStateException e){ // 이메일이 중복일경우 동작
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "member/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비번 확인");

        return "member/loginForm";
    }

}
