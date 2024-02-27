package com.weapon.shop.service;

import com.weapon.shop.dto.MemberFormDto;
import com.weapon.shop.entity.Member;
import com.weapon.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 회원가입폼의 내용을 데이터베이스에 저장 ( 회원가입 )
    public void saveMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = Member.createMember(memberFormDto,passwordEncoder);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 스프링 시큐리티 사용시 커스텀로그인 DB의 데이터로 로그인진행시 필요한 메서드
        return null;
    }
}
