package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.MaterialRepository;
import com.alena.jewelryproject.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
}
