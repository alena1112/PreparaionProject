package com.alena.preparationproject.mvc.service;

import com.alena.preparationproject.dao.MaterialDao;
import com.alena.preparationproject.mvc.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {
    @Autowired
    private MaterialDao materialDao;

    public List<Material> getAllMaterials() {
        return materialDao.getAll();
    }
}
