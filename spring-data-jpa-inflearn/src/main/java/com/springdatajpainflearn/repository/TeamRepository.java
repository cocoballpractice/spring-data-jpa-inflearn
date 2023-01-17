package com.springdatajpainflearn.repository;

import com.springdatajpainflearn.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
