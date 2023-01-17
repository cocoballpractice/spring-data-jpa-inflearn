package com.springdatajpainflearn.repository;

import com.springdatajpainflearn.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
