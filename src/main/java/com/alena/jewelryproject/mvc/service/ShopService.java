package com.alena.jewelryproject.mvc.service;

import com.alena.jewelryproject.dao.ShopDao;
import com.alena.jewelryproject.mvc.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopDao shopDao;

    public List<Shop> getAllShops() {
        return shopDao.getAll();
    }
}