package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Material;
import com.alena.jewelryproject.model.enums.Shop;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class MaterialRepository extends SimpleJpaRepository<Material, Long> {
    private final EntityManager entityManager;

    public MaterialRepository(EntityManager entityManager) {
        super(Material.class, entityManager);
        this.entityManager = entityManager;
    }

    public List<Material> findMaterials(Shop shop, Long orderId, String materialName) {
        TypedQuery<Material> query = entityManager.createQuery(
                "select m from Material m where 1 = 1" +
                        (shop != null ? " and m.order.shop.name = :shop" : "") +
                        (orderId != null ? " and m.order.id = :orderId" : "") +
                        (StringUtils.isNotBlank(materialName) ? " and m.name like :materialName" : ""), Material.class);
        if (shop != null) {
            query.setParameter("shop", shop.getId());
        }
        if (orderId != null) {
            query.setParameter("orderId", orderId);
        }
        if (StringUtils.isNotBlank(materialName)) {
            query.setParameter("materialName", materialName);
        }
        return query.getResultList();
    }
}
