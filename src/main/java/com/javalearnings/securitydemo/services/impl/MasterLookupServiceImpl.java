package com.javalearnings.securitydemo.services.impl;

import com.javalearnings.securitydemo.entities.common.RefCountry;
import com.javalearnings.securitydemo.entities.common.RefRole;
import com.javalearnings.securitydemo.entities.common.RefState;
import com.javalearnings.securitydemo.model.common.ResponseSelect;
import com.javalearnings.securitydemo.model.common.SelectDropdowns;
import com.javalearnings.securitydemo.repositories.common.RefCountryRepository;
import com.javalearnings.securitydemo.repositories.common.RefRoleRepository;
import com.javalearnings.securitydemo.repositories.common.RefStateRepository;
import com.javalearnings.securitydemo.services.MasterLookupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.cache.CacheManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterLookupServiceImpl implements MasterLookupService {

    private final RefCountryRepository refCountryRepository;

    private final RefStateRepository refStateRepository;

    private final RefRoleRepository refRoleRepository;

    @Autowired
    private CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames="masterLookupCacheWithParam", key = "#path + '_' + #id")
    public ResponseSelect fetchListWithParam(String path, Integer id) {
        log.debug("I am in fetchList");
        List<SelectDropdowns> selectDropdownsList = getSelectDropdownsWithParam(path, id);
        log.debug("selectDropdownsList {}", selectDropdownsList.size());
        return new ResponseSelect(selectDropdownsList);
    }

    @Override
    @Cacheable(cacheNames="masterLookupCache", key = "#path")
    public ResponseSelect fetchList(String path) {
        log.debug("I am in fetchList");

        List<SelectDropdowns> selectDropdownsList = getSelectDropdowns(path);
        log.debug("selectDropdownsList {}", selectDropdownsList.size());
        return new ResponseSelect(selectDropdownsList);
    }

    private List<SelectDropdowns> getSelectDropdownsWithParam(String path, Integer id) {
        if (path.contains("/master/fetchStateList")) {
            return getStateSelectDropdowns(id);
        }
        return null;
    }

    private List<SelectDropdowns> getSelectDropdowns(String path) {
        if (path.contains("/master/fetchCountryList")) {
            return getCountrySelectDropdowns();
        } else if (path.contains("/master/fetchRolesList")) {
            return getRolesSelectDropdowns();
        } else if (path.contains("/master/fetchActiveInd")) {
            return getYesOrNo(false);
        } else if (path.contains("/master/YesOrNo")) {
            return getYesOrNo(true);
        } else if (path.contains("/master/accountingType")) {
            return getAccountingType();
        } else if (path.contains("/master/renewalDate")) {
            return getRenewalDate();
        }else if (path.contains("/master/dayOfpayment")) {
            return getDayOfpayment();
        }
                return null;
    }

    private static List<SelectDropdowns> getYesOrNo(boolean flag) {
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        SelectDropdowns selectDropdowns = new SelectDropdowns();
        if (flag) {
            selectDropdowns.setCode("Yes");
        } else {
            selectDropdowns.setCode("Y");
        }
        selectDropdowns.setDescription("Yes");
        selectDropdownsList.add(selectDropdowns);

        selectDropdowns = new SelectDropdowns();
        if (flag) {
            selectDropdowns.setCode("No");
        } else {
            selectDropdowns.setCode("N");
        }
        selectDropdowns.setDescription("No");
        selectDropdownsList.add(selectDropdowns);
        return selectDropdownsList;
    }

    private static List<SelectDropdowns> getAccountingType() {
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        SelectDropdowns selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("Shift Wise");
        selectDropdowns.setDescription("Shift Wise");
        selectDropdownsList.add(selectDropdowns);
        selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("Day Wise");
        selectDropdowns.setDescription("Day Wise");
        selectDropdownsList.add(selectDropdowns);
        return selectDropdownsList;
    }

    private static List<SelectDropdowns> getRenewalDate() {
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        SelectDropdowns selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("Recurring");
        selectDropdowns.setDescription("Recurring");
        selectDropdownsList.add(selectDropdowns);
        selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("Monthly");
        selectDropdowns.setDescription("Monthly");
        selectDropdownsList.add(selectDropdowns);
        selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("Annual");
        selectDropdowns.setDescription("Annual");
        selectDropdownsList.add(selectDropdowns);
        selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("Quarterly");
        selectDropdowns.setDescription("Quarterly");
        selectDropdownsList.add(selectDropdowns);
        return selectDropdownsList;
    }

    private List<SelectDropdowns> getCountrySelectDropdowns() {
        List<RefCountry> refCountryList = refCountryRepository.findAll();
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        if (refCountryList != null && !refCountryList.isEmpty()) {
            refCountryList.forEach(
                    refCountry -> {
                        SelectDropdowns selectDropdowns = new SelectDropdowns();
                        selectDropdowns.setCode(String.valueOf(refCountry.getCountryId()));
                        selectDropdowns.setDescription(refCountry.getCountryName());
                        selectDropdownsList.add(selectDropdowns);
                    }
            );
        }
        return selectDropdownsList;
    }

    private List<SelectDropdowns> getRolesSelectDropdowns() {
        List<RefRole> refRoleList = refRoleRepository.findByActiveInd();
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        if (refRoleList != null && !refRoleList.isEmpty()) {
            refRoleList.forEach(
                    refRole -> {
                        SelectDropdowns selectDropdowns = new SelectDropdowns();
                        selectDropdowns.setCode(String.valueOf(refRole.getRoleId()));
                        selectDropdowns.setDescription(refRole.getRoleName());
                        selectDropdownsList.add(selectDropdowns);
                    }
            );
        }
        return selectDropdownsList;
    }

    private List<SelectDropdowns> getStateSelectDropdowns(Integer id) {
        List<RefState> refStateList = refStateRepository.findByCountryId(id);
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        if (refStateList != null && !refStateList.isEmpty()) {
            refStateList.forEach(
                    refState -> {
                        SelectDropdowns selectDropdowns = new SelectDropdowns();
                        selectDropdowns.setCode(String.valueOf(refState.getStateId()));
                        selectDropdowns.setDescription(refState.getStateName());
                        selectDropdownsList.add(selectDropdowns);
                    }
            );
        }
        return selectDropdownsList;
    }


    private static List<SelectDropdowns> getDayOfpayment() {
        List<SelectDropdowns> selectDropdownsList = new ArrayList<>();
        SelectDropdowns selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("5th Of Every Month");
        selectDropdowns.setDescription("5th Of Every Month");
        selectDropdownsList.add(selectDropdowns);
        selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("3 Jan Annualy");
        selectDropdowns.setDescription("3 Jan Annualy");
        selectDropdownsList.add(selectDropdowns);
        selectDropdowns = new SelectDropdowns();
        selectDropdowns.setCode("One Time");
        selectDropdowns.setDescription("One Time");
        selectDropdownsList.add(selectDropdowns);
        return selectDropdownsList;
    }


    @Override
    public void evictAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

}
