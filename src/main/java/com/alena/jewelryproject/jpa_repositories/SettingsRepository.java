package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Settings getByKey(String key);

    List<Settings> findAllByOrderByKey();
}
