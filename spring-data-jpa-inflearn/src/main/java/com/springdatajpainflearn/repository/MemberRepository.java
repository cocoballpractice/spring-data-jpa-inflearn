package com.springdatajpainflearn.repository;

import com.springdatajpainflearn.dto.MemberDto;
import com.springdatajpainflearn.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username")String username);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 칼럼 값 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // Dto 조회
    @Query("select new com.springdatajpainflearn.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t ")
    List<MemberDto> findMemberDto();

    // IN 절 구현
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // 페이징
    // Page<Member> findByAge(int age, Pageable pageable); // JPA에서는 offset으로 페이지 가져올 시 0부터 시작해야 함

    // 페이징 심화 (컨텐츠 가져오는 쿼리, count 쿼리 분리)
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    // bulk update
    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // fetch join
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // EntityGraph, 공통 인터페이스 내 findAll() 재정의
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // query hint, 단순 조회용 쿼리
    @QueryHints(value=@QueryHint(name = "org.hibernate.readOnly", value="true"))
    Member findReadOnlyByUsername(String username);

    // lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

}
