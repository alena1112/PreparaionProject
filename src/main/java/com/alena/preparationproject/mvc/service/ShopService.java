package com.alena.preparationproject.mvc.service;

import com.alena.preparationproject.dao.ShopDao;
import com.alena.preparationproject.mvc.model.Shop;
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
