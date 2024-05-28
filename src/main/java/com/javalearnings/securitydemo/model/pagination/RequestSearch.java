package com.javalearnings.securitydemo.model.pagination;

import jakarta.validation.Valid;

public record RequestSearch(@Valid RequestPageModel requestPageModel) {
}