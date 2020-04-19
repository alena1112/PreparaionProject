package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
