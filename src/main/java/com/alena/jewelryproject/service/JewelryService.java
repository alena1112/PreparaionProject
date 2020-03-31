package com.alena.jewelryproject.service;

import com.alena.jewelryproject.dao.JewelryDao;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JewelryService {
    @Autowired
    private JewelryDao jewelryDao;

    public List<Jewelry> getAllUnhiddenJewelries() {
        return jewelryDao.getAllUnhidden();
    }

    public List<Jewelry> getAllJewelries() {
        return jewelryDao.getAll();
    }

    public List<Jewelry> getNewUnhiddenJewelries(Date fromDate, int maxCount) {
        return jewelryDao.getAllUnhidden(maxCount);
    }

    public List<Jewelry> getUnhiddenJewelries(JewelryType type) {
        return jewelryDao.getAllUnhidden(type);
    }

    public Jewelry getJewelry(long id) {
        return jewelryDao.get(id)
                .orElse(null);
    }

    public void update(Jewelry jewelry) {
        jewelryDao.update(jewelry);
    }

    public void save(Jewelry jewelry) {
        jewelryDao.save(jewelry);
    }

    public void deleteJewelry(long id) {
        jewelryDao.delete(id);
    }

    public void sellJewelries(List<Jewelry> jewelries) {
        for (Jewelry jewelry : jewelries) {
            jewelry.setSold(true);
            save(jewelry);
        }
    }
}
