package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
