package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.JewelryMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelryMaterialRepository extends JpaRepository<JewelryMaterial, Long> {
    List<JewelryMaterial> getJewelryMaterialByJewelry(Jewelry jewelry);
}
