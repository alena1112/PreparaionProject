package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.SettingsRepository;
import com.alena.jewelryproject.model.Settings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {
    private static final Logger log = LoggerFactory.getLogger(SettingsService.class);

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings getSetting(Long id) {
        return settingsRepository.findById(id).orElse(null);
    }

    public String getSettingByKey(String key, String defaultValue) {
        String settingValue = getSettingByKey(key);
        return StringUtils.isNotBlank(settingValue) ? settingValue : defaultValue;
    }

    public String getSettingByKey(String key) {
        Settings settings = settingsRepository.getByKey(key);
        if (settings == null) {
            log.warn(String.format("Did not find setting with key %s", key));
            return null;
        } else {
            log.info(String.format("Found setting %s, value is %s", key, settings.getValue()));
            return settings.getValue();
        }
    }

    public List<Settings> getAllSettings() {
        return settingsRepository.findAll();
    }

    public void save(Settings settings) {
        settingsRepository.save(settings);
    }

    public synchronized void update(Settings settings) {
        settingsRepository.save(settings);
    }

    public synchronized void delete(Long id) {
        settingsRepository.deleteById(id);
    }
}
