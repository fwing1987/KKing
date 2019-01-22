package com.kking.generator.utils;

import org.apache.velocity.app.Velocity;

import java.util.Properties;

public class VelocityInitUtil {
    /**
     * 初始化Velocity引擎
     * --VelocityEngine是单例模式，线程安全
     * @throws Exception
     */
    public static void initVelocity() {
        try {
            Properties p = new Properties();
            /**
             * velocity.properties配置定义
             * file.resource.loader.class = org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
             * ENCODING_DEFAULT = UTF-8
             * OUTPUT_ENCODING = UTF-8
             */
            //加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            //定义字符集
            p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
            p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
