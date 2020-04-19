package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
