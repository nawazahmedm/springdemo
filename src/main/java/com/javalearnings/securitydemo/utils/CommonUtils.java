package com.javalearnings.securitydemo.utils;

import com.javalearnings.securitydemo.model.pagination.RequestPageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommonUtils {

    public static Pageable getPageable(RequestPageModel requestPageModel) {
        Sort sort;
        Pageable pageable;
        int page = requestPageModel.page();
        int size = requestPageModel.size();

        if (Objects.isNull(page) || page < 0) page = 0;
        if (Objects.isNull(size) || size < 1) size = 10;

        if (StringUtils.isNotBlank(requestPageModel.sortFieldName()) && StringUtils.isNotBlank(requestPageModel.sortOrder())) {
            sort = Sort.by(requestPageModel.sortOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, requestPageModel.sortFieldName());
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return pageable;
    }

}