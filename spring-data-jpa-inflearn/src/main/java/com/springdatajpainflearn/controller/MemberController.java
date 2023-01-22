package com.springdatajpainflearn.controller;

import com.springdatajpainflearn.dto.MemberDto;
import com.springdatajpainflearn.entity.Member;
import com.springdatajpainflearn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    // 컨버터 사용 전
    // id는 PK
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 컨버터 사용 후
    // 권장은 안함... 조회용으로만 사용해야 함.
    // 트랜잭션이 없는 범위에서 엔티티를 조회하였으므로 엔티티 변경해도 DB 반영이 안됨
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    // 페이징 및 정렬
    // URL에 page, size, sort 파라미터 넣으면 바로 적용됨
    // limit(size) 기본값 20
    @GetMapping("/members")
    public Page<Member> list(@PageableDefault(size = 5, sort="username") Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    // 엔티티가 아닌 Dto로 페이징 필요
    @GetMapping("/api/v1/members/")
    public Page<MemberDto> list2(@PageableDefault(size = 5, sort="username") Pageable pageable) {
        Page<MemberDto> map = memberRepository.findAll(pageable).map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));

        return map;
    }
}
