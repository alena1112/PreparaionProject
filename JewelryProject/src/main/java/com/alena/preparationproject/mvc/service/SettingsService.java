package com.alena.preparationproject.mvc.service;

import com.alena.preparationproject.dao.SettingsDao;
import com.alena.preparationproject.mvc.model.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {
    private static final Logger log = LoggerFactory.getLogger(SettingsService.class);

    @Autowired
    private SettingsDao settingsDao;

    public Settings getSettingsByKey(String key) {
        Settings settings = settingsDao.get(key)
                .orElse(null);
        if (settings == null) {
            log.warn(String.format("Did not find setting with key %s", key));
        } else {
            log.info(String.format("Found setting %s, value is %s", key, settings.getValue()));
        }
        return settings;
    }

    public List<Settings> getAllSettings() {
        return settingsDao.getAll();
    }
}
