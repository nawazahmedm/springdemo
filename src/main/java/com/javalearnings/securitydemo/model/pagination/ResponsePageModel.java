package com.javalearnings.securitydemo.model.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponsePageModel<T> {
    List<T> list;
    int totalPages;
    long totalProjectSummary;
    int page;
    int size;

    public ResponsePageModel(Page<T> page) {
        this.list = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalProjectSummary = page.getTotalElements();
        this.page = page.getNumber();
        this.size = page.getSize();
    }
}