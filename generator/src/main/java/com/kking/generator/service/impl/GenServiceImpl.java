package com.kking.generator.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kking.common.utils.StringUtils;
import com.kking.generator.entity.ColumnInfo;
import com.kking.generator.entity.TableInfo;
import com.kking.generator.mapper.GenMapper;
import com.kking.generator.service.GenService;
import com.kking.generator.utils.GenUtil;
import com.kking.generator.utils.VelocityInitUtil;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class GenServiceImpl implements GenService {
    @Autowired
    GenMapper genMapper;

    public List<ColumnInfo> getColumns(String tableName){
        List<ColumnInfo> columns = genMapper.selectColumns(tableName);
        for(ColumnInfo column: columns){
            String camelName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setCamelName(StringUtils.uncapitalize(camelName));
            column.setXCamelName(camelName);
            column.setJavaType(GenUtil.JavaTypeMap.get(column.getDataType()));
        }
        return columns;
    }

    @Override
    public byte[] generateCode(String tableParam,String packageName) {
        Object retObj = JSON.parse(tableParam);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(out);
        if(retObj instanceof JSONArray){
            for(int i = 0;i < ((JSONArray) retObj).size();i++){
                String tableName = ((JSONArray) retObj).getString(i);
                TableInfo table = genMapper.selectTable(tableName);
                List<ColumnInfo> columns = getColumns(tableName);
                generateCode(table,columns,zip,packageName);
            }
        }else{
            TableInfo table = genMapper.selectTable(tableParam);
            List<ColumnInfo> columns = getColumns(tableParam);
            generateCode(table,columns,zip,packageName);
        }


        IOUtils.closeQuietly(zip);
        return out.toByteArray();
    }

    @Override
    public byte[] generateCode(List<String> tableName,String packageName) {

        return new byte[0];
    }

    private void generateCode(TableInfo table, List<ColumnInfo> columns, ZipOutputStream zip,String packageName){
        VelocityInitUtil.initVelocity();

        table.setXCamelName(StringUtils.convertToCamelCase(table.getTableName()));
        table.setCamelName(StringUtils.uncapitalize(table.getXCamelName()));
        table.setPrimaryKey(columns.get(0));
        table.setColumns(columns);
        table.setPackageName(packageName);

        VelocityContext velocityContext = getVelocityContext(table);

        List<String> templates = GenUtil.getTemplates();
        try {
            for(String template:templates){
                StringWriter strWriter = new StringWriter();
                Template tpl = Velocity.getTemplate(template);
                velocityContext.put("moduleName",GenUtil.getLastPackage(template));
                tpl.merge(velocityContext,strWriter);
                zip.putNextEntry(new ZipEntry(GenUtil.getFileName(template,table.getXCamelName())));
                System.out.println(strWriter.toString());
                IOUtils.write(strWriter.toString(),zip,"UTF-8");
                IOUtils.closeQuietly(strWriter);
                zip.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VelocityContext getVelocityContext(TableInfo table){

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName",table.getTableName());
        velocityContext.put("table",table);
        velocityContext.put("packageName",table.getPackageName());
        velocityContext.put("primaryKey",table.getPrimaryKey());
        velocityContext.put("columns",table.getColumns());
        return velocityContext;

    }
}
