package com.javalearnings.securitydemo.controllers.impl;

import com.javalearnings.securitydemo.constants.SAConstants;
import com.javalearnings.securitydemo.controllers.MasterLookupController;
import com.javalearnings.securitydemo.exceptions.BusinessException;
import com.javalearnings.securitydemo.exceptions.GenericException;
import com.javalearnings.securitydemo.model.common.ResponseBoolean;
import com.javalearnings.securitydemo.model.common.ResponseSelect;
import com.javalearnings.securitydemo.services.MasterLookupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class MasterLookupControllerImpl implements MasterLookupController {

    private final MasterLookupService masterLookupService;

    public MasterLookupControllerImpl(MasterLookupService masterLookupService) {
        this.masterLookupService = masterLookupService;
    }

    @Override
    public ResponseEntity<ResponseSelect> fetchListWithParam(Integer userId, Integer id, HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getRequestURI();
        log.debug("MasterLookupControllerImpl : fetchListWithParam : Start : path {}", path);
        try {
            ResponseSelect responseSelect = masterLookupService.fetchListWithParam(path, id);
            ResponseEntity<ResponseSelect> responseEntity = new ResponseEntity<>(responseSelect, HttpStatus.OK);
            log.debug("MasterLookupControllerImpl : fetchListWithParam : responseEntity {}", responseEntity);
            return responseEntity;
        } catch (BusinessException businessException) {
            log.error("MasterLookupControllerImpl : fetchListWithParam : BusinessException : ", businessException);
            throw new BusinessException(businessException.getCode(), businessException.getMessage(), businessException.getHttpStatus());
        } catch (Exception exception) {
            log.error("MasterLookupControllerImpl : fetchListWithParam : Exception : ", exception);
            throw new GenericException(exception);
        }
    }

    @Override
    public ResponseEntity<ResponseSelect> fetchList(Integer userId, HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getRequestURI();
        log.debug("MasterLookupControllerImpl : fetchList : Start : path {}", path);
        try {
            ResponseSelect responseSelect = masterLookupService.fetchList(path);
            ResponseEntity<ResponseSelect> responseEntity = new ResponseEntity<>(responseSelect, HttpStatus.OK);
            log.debug("MasterLookupControllerImpl : fetchList : responseEntity {}", responseEntity);
            return responseEntity;
        } catch (BusinessException businessException) {
            log.error("MasterLookupControllerImpl : fetchList : BusinessException : ", businessException);
            throw new BusinessException(businessException.getCode(), businessException.getMessage(), businessException.getHttpStatus());
        } catch (Exception exception) {
            log.error("MasterLookupControllerImpl : fetchList : Exception : ", exception);
            throw new GenericException(exception);
        }
    }

    @Override
    public ResponseEntity<ResponseBoolean> refreshCache() {
        log.debug("MasterLookupControllerImpl : refreshCache : Start : path");
        try {
            masterLookupService.evictAllCaches();
            Arrays.stream(SAConstants.MASTER_LIST).forEach(
                    master -> {
                        log.debug("MasterLookupControllerImpl : refreshCache : Start : master {}", master);

                        masterLookupService.fetchList(master);
                    }
            );
            ResponseEntity responseEntity = new ResponseEntity<>(new ResponseBoolean(Boolean.TRUE), HttpStatus.OK);
            log.debug("MasterLookupControllerImpl : refreshCache : responseEntity {}", responseEntity);
            return responseEntity;
        } catch (BusinessException businessException) {
            log.error("MasterLookupControllerImpl : refreshCache : BusinessException : ", businessException);
            throw new BusinessException(businessException.getCode(), businessException.getMessage(), businessException.getHttpStatus());
        } catch (Exception exception) {
            log.error("MasterLookupControllerImpl : refreshCache : Exception : ", exception);
            throw new GenericException(exception);
        }
    }
    
}
