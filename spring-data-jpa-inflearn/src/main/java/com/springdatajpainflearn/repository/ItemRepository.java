package com.springdatajpainflearn.repository;

import com.springdatajpainflearn.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
