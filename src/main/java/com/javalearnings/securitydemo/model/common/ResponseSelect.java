package com.javalearnings.securitydemo.model.common;

import java.io.Serializable;
import java.util.List;

public record ResponseSelect(List<SelectDropdowns> selectDropdownList) implements Serializable {

}
