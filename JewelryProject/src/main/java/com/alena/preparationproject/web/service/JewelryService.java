package com.alena.preparationproject.web.service;

import com.alena.preparationproject.dao.Dao;
import com.alena.preparationproject.web.model.Jewelry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public void save(long id, Map<String, Object> params) {
        Jewelry jewelry = getJewelry(id);
        if (jewelry != null) {
            synchronized (jewelryDao) {
                jewelryDao.update(jewelry, params);
            }
        }
    }

    public void saveNew(Jewelry jewelry) {
        jewelryDao.save(jewelry);
    }

    public void deleteJewelry(long id) {
        Jewelry jewelry = getJewelry(id);
        if (jewelry != null) {
            jewelryDao.delete(jewelry);
        }
    }
}
