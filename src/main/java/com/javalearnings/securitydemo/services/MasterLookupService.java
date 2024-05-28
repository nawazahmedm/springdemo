package com.javalearnings.securitydemo.services;

import com.javalearnings.securitydemo.model.common.ResponseSelect;

public interface MasterLookupService {

    ResponseSelect fetchListWithParam(String path, Integer id);

    ResponseSelect fetchList(String path);

    /**
     * Clear the Cache
     *
     */
    void evictAllCaches();

}
