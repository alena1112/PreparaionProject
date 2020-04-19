package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.JewelryRepository;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JewelryService {
    @Autowired
    private JewelryRepository jewelryRepository;

    public List<Jewelry> getAllUnhiddenJewelries() {
        return jewelryRepository.getAllUnhidden();
    }

    public List<Jewelry> getAllJewelries() {
        return jewelryRepository.findAll();
    }

    public List<Jewelry> getNewUnhiddenJewelries(Date fromDate, int maxCount) {
        return jewelryRepository.getAllUnhidden(PageRequest.of(0, maxCount));
    }

    public List<Jewelry> getUnhiddenJewelries(JewelryType type) {
        return jewelryRepository.getAllUnhidden(type);
    }

    public Jewelry getJewelry(long id) {
        return jewelryRepository.findById(id).orElse(null);
    }

    public void update(Jewelry jewelry) {
        jewelryRepository.save(jewelry);
    }

    public void save(Jewelry jewelry) {
        jewelryRepository.save(jewelry);
    }

    public void deleteJewelry(long id) {
        jewelryRepository.deleteById(id);
    }

    public void sellJewelries(List<Jewelry> jewelries) {
        for (Jewelry jewelry : jewelries) {
            jewelry.setSold(true);
            save(jewelry);
        }
    }
}
