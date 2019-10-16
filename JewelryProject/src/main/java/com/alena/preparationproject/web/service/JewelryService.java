package com.alena.preparationproject.web.service;

import com.alena.preparationproject.dao.Dao;
import com.alena.preparationproject.web.model.Jewelry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JewelryService {
    @Autowired
    private Dao<Jewelry> jewelryDao;

    public List<Jewelry> getAllJewelries() {
        return jewelryDao.getAll();
    }

    public Jewelry getJewelry(long id) {
        return jewelryDao.get(id)
                .orElse(null);
    }
}
