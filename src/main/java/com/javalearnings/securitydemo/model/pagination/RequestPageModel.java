package com.javalearnings.securitydemo.model.pagination;

import jakarta.validation.Valid;

public record RequestPageModel(@Valid() int page, @Valid int size, @Valid String sortFieldName, @Valid String sortOrder) {
}