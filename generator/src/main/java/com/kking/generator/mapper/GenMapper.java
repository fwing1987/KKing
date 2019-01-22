package com.kking.generator.mapper;

import com.kking.generator.entity.ColumnInfo;
import com.kking.generator.entity.TableInfo;

import java.util.List;

public interface GenMapper {
    public TableInfo selectTable(String tableName);
    public List<ColumnInfo> selectColumns(String tableName);
}
