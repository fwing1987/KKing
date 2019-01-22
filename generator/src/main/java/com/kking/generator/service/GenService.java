package com.kking.generator.service;

import java.util.List;

public interface GenService {
    public byte[] generateCode(String tableName,String packageName);
    public byte[] generateCode(List<String> tableName,String packageName);
}
