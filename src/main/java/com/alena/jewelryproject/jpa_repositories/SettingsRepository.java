package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Settings getByKey(String key);
}
