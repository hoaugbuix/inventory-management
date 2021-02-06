package com.inventory.dev.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static ConfigLoader instance = null;
    String proFileName = "config.properties";
    private Properties properties = null;

    private ConfigLoader() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(proFileName);
        if (inputStream != null) {
            properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            //	synchronized (ConfigLoader.class) {
            instance = new ConfigLoader();
            //	}

        }
        return instance;
    }

    public String getValue(String key) {
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return null;
    }
}
