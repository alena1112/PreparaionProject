package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialOrderRepository extends JpaRepository<MaterialOrder, Long> {
}
