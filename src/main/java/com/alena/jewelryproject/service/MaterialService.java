package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.JewelryMaterialRepository;
import com.alena.jewelryproject.jpa_repositories.MaterialOrderRepository;
import com.alena.jewelryproject.jpa_repositories.MaterialRepository;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.JewelryMaterial;
import com.alena.jewelryproject.model.Material;
import com.alena.jewelryproject.model.MaterialOrder;
import com.alena.jewelryproject.model.enums.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialOrderRepository materialOrderRepository;
    @Autowired
    private JewelryMaterialRepository jewelryMaterialRepository;

    public List<JewelryMaterial> getJewelryMaterials(Jewelry jewelry) {
        return jewelryMaterialRepository.getJewelryMaterialByJewelry(jewelry);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public List<Material> getAllJewelryMaterials(Long jewelryId) {
        return materialRepository.findAll();
    }

    public List<MaterialOrder> getMaterialOrders() {
        return materialOrderRepository.findAll();
    }

    public List<Material> findMaterials(Shop shop, Long orderId, String materialName) {
        return materialRepository.findMaterials(shop, orderId, materialName);
    }
}
