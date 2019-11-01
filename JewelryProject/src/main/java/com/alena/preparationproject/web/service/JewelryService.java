package com.alena.preparationproject.web.service;

import com.alena.preparationproject.dao.JewelryDao;
import com.alena.preparationproject.web.model.Jewelry;
import com.alena.preparationproject.web.model.enums.JewelryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JewelryService {
    @Autowired
    private JewelryDao jewelryDao;

    public List<Jewelry> getAllJewelries() {
        return jewelryDao.getAll();
    }

    public List<Jewelry> getNewJewelries(Date fromDate, int maxCount) {
        return jewelryDao.getAll();
    }

    public List<Jewelry> getJewelries(JewelryType type) {
        return jewelryDao.getAll(type);
    }

    public Jewelry getJewelry(long id) {
        return jewelryDao.get(id)
                .orElse(null);
    }

    public void save(Jewelry jewelry) {
        jewelryDao.update(jewelry);
    }

    public void saveNew(Jewelry jewelry) {
        jewelryDao.save(jewelry);
    }

    public void deleteJewelry(long id) {
        jewelryDao.delete(id);
    }
}
