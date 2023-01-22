package com.springdatajpainflearn.repository;

import com.springdatajpainflearn.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
