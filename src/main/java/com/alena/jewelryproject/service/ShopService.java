package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.ShopRepository;
import com.alena.jewelryproject.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }
}
