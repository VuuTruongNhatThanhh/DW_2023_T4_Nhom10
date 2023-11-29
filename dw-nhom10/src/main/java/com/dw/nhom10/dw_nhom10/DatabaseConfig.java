package com.dw.nhom10.dw_nhom10;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private String jdbcUrl;
    private String username;
    private String password;

    public DatabaseConfig(String dbName) {
        loadConfig(dbName);
    }

    private void loadConfig(String dbName) {
    	try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
    	    Properties prop = new Properties();
    	    prop.load(input);

            // Đọc thông tin từ file config cho cơ sở dữ liệu cụ thể
            jdbcUrl = prop.getProperty(dbName + ".url");
            username = prop.getProperty(dbName + ".username");
            password = prop.getProperty(dbName + ".password");

        } catch (IOException ex) {
            ex.printStackTrace();
            // Xử lý khi có lỗi đọc file cấu hình
        }
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

