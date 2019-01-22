package com.kking.generator.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenUtil {
    public static final Map<String,String> JavaTypeMap = new HashMap<>();
    static
    {
        JavaTypeMap.put("tinyint", "Integer");
        JavaTypeMap.put("smallint", "Integer");
        JavaTypeMap.put("mediumint", "Integer");
        JavaTypeMap.put("int", "Integer");
        JavaTypeMap.put("integer", "integer");
        JavaTypeMap.put("bigint", "Long");
        JavaTypeMap.put("float", "Float");
        JavaTypeMap.put("double", "Double");
        JavaTypeMap.put("decimal", "BigDecimal");
        JavaTypeMap.put("bit", "Boolean");
        JavaTypeMap.put("char", "String");
        JavaTypeMap.put("varchar", "String");
        JavaTypeMap.put("tinytext", "String");
        JavaTypeMap.put("text", "String");
        JavaTypeMap.put("mediumtext", "String");
        JavaTypeMap.put("longtext", "String");
        JavaTypeMap.put("time", "Date");
        JavaTypeMap.put("date", "Date");
        JavaTypeMap.put("datetime", "Date");
        JavaTypeMap.put("timestamp", "Date");
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplates()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/mapper/Mapper.xml.vm");
        templates.add("vm/mapper/Mapper.java.vm");
        templates.add("vm/entity/.java.vm");
        templates.add("vm/service/Service.java.vm");
        templates.add("vm/service/impl/ServiceImpl.java.vm");
        return templates;
    }

    public static String getFileName(String template, String className) {
        //vm/mapper/Mapper.xml.vm -> Mapper.xml
        String tmpName = template.substring(template.lastIndexOf("/")+1);
        String pathName = template.substring(template.indexOf("/")+1,template.lastIndexOf("/")+1);
        tmpName = tmpName.substring(0,tmpName.lastIndexOf("."));
        return pathName + className + tmpName;
    }

    public static String getLastPackage(String template){
        //vm/mapper/Mapper.xml.vm -> mapper
        return template.substring(template.indexOf("/")+1,template.lastIndexOf("/")).replace("/",".");
    }
}
