package com.kking.generator.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnInfo {
    private String columnName;
    private String camelName;
    private String xCamelName;
    private String dataType;
    private String javaType;
}
