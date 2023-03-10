package com.springdatajpainflearn.repository;

import com.springdatajpainflearn.dto.MemberDto;
import com.springdatajpainflearn.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void testMember() {
        Member member = new Member("MemberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();

        assertThat(all.size()).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);

    }

    @Test
    public void paging() {
        // Given
        memberRepository.save(new Member("test1", 10));
        memberRepository.save(new Member("test2", 10));
        memberRepository.save(new Member("test3", 10));
        memberRepository.save(new Member("test4", 10));
        memberRepository.save(new Member("test5", 10));
        memberRepository.save(new Member("test6", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // When
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        // Dto로 변환
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // Then
    }

    @Test
    public void queryHint() {
        // Given
        Member member1 = memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // When
        Member findMember = memberRepository.findById(member1.getId()).get();
        // findMember.setUsername("member2");

        em.flush();

        // Then

    }

}