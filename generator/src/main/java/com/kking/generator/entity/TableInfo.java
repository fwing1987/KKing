package com.kking.generator.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableInfo {
    private String tableName;
    private String tableComment;
    private String camelName;//驼峰但首字母小写
    private String xCamelName;//驼峰但首字母大写
    private String packageName;
    private ColumnInfo primaryKey;
    private List<ColumnInfo> columns;
}
