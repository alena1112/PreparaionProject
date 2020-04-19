package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.PromotionalCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocodeRepository extends JpaRepository<PromotionalCode, Long> {
    PromotionalCode getByCode(String code);
}
