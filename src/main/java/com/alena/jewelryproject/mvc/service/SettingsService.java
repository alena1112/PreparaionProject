package com.alena.jewelryproject.mvc.service;

import com.alena.jewelryproject.dao.SettingsDao;
import com.alena.jewelryproject.mvc.model.Settings;
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
    private SettingsDao settingsDao;

    public Settings getSetting(Long id) {
        return settingsDao.get(id).orElse(null);
    }

    public String getSettingByKey(String key, String defaultValue) {
        String settingValue = getSettingByKey(key);
        return StringUtils.isNotBlank(settingValue) ? settingValue : defaultValue;
    }

    public String getSettingByKey(String key) {
        Settings settings = settingsDao.get(key)
                .orElse(null);
        if (settings == null) {
            log.warn(String.format("Did not find setting with key %s", key));
            return null;
        } else {
            log.info(String.format("Found setting %s, value is %s", key, settings.getValue()));
            return settings.getValue();
        }
    }

    public List<Settings> getAllSettings() {
        return settingsDao.getAll();
    }

    public void save(Settings settings) {
        settingsDao.save(settings);
    }

    public synchronized void update(Settings settings) {
        settingsDao.update(settings);
    }

    public synchronized void delete(Long id) {
        settingsDao.delete(id);
    }
}
