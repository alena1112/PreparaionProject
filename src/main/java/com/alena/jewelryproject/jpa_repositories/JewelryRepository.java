package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelryRepository extends JpaRepository<Jewelry, Long> {

    default List<Jewelry> getAllUnhidden(Pageable pageable) {
        return findAllByIsHideIsFalseOrderByRatingDesc(pageable);
    }

    default List<Jewelry> getAllNewUnhidden(Pageable pageable) {
        return findAllByIsHideIsFalseOrderByCreatedDateDescRatingDesc(pageable);
    }

    default List<Jewelry> getAllUnhidden() {
        return findAllByIsHideIsFalse();
    }

    default List<Jewelry> getAllUnhidden(JewelryType type, Pageable pageable) {
        return findAllByIsHideIsFalseAndTypeOrderByRatingDesc(type, pageable);
    }

    int countJewelriesByIsHideIsFalse();

    int countJewelriesByIsHideIsFalseAndType(JewelryType type);

    List<Jewelry> findAllByIsHideIsFalseOrderByRatingDesc(Pageable pageable);

    List<Jewelry> findAllByIsHideIsFalse();

    List<Jewelry> findAllByIsHideIsFalseAndTypeOrderByRatingDesc(JewelryType type, Pageable pageable);

    List<Jewelry> findAllByIsHideIsFalseOrderByCreatedDateDescRatingDesc(Pageable pageable);
}
