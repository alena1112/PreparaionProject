package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    default List<Jewelry> getAllUnhidden() {
        return findAllByIsHideIsFalseOrderByIsSold();
    }

    default List<Jewelry> getAllUnhidden(Pageable pageable) {
        return findAllByIsHideIsFalseOrderByIsSold(pageable);
    }

    default List<Jewelry> getAllUnhidden(JewelryType type) {
        return findAllByIsHideIsFalseAndTypeOrderByIsSold(type);
    }

    List<Jewelry> findAllByIsHideIsFalseOrderByIsSold();

    List<Jewelry> findAllByIsHideIsFalseOrderByIsSold(Pageable pageable);

    List<Jewelry> findAllByIsHideIsFalseAndTypeOrderByIsSold(JewelryType type);
}
