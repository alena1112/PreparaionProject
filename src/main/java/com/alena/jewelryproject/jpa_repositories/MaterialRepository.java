package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
