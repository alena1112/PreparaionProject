package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelryRepository extends JpaRepository<Jewelry, Long> {

    default List<Jewelry> getAllUnhidden(Pageable pageable) {
        return findAllByIsHideIsFalseOrderByIdAscIsSoldAsc(pageable);
    }

    default List<Jewelry> getAllNewUnhidden(Pageable pageable) {
        return findAllByIsHideIsFalseOrderByIsSoldAscCreatedDateDesc(pageable);
    }

    default List<Jewelry> getAllUnhidden() {
        return findAllByIsHideIsFalse();
    }

    default List<Jewelry> getAllUnhidden(JewelryType type, Pageable pageable) {
        return findAllByIsHideIsFalseAndTypeOrderByIdAscIsSoldAsc(type, pageable);
    }

    int countJewelriesByIsHideIsFalse();

    int countJewelriesByIsHideIsFalseAndType(JewelryType type);

    List<Jewelry> findAllByIsHideIsFalseOrderByIdAscIsSoldAsc(Pageable pageable);

    List<Jewelry> findAllByIsHideIsFalse();

    List<Jewelry> findAllByIsHideIsFalseAndTypeOrderByIdAscIsSoldAsc(JewelryType type, Pageable pageable);

    List<Jewelry> findAllByIsHideIsFalseOrderByIsSoldAscCreatedDateDesc(Pageable pageable);
}
