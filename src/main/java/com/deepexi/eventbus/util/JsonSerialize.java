package com.deepexi.eventbus.util;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Drizzt
 * @description {描述}
 * @date 2019/5/9  21:07
 */
public class JsonSerialize {
    private static final Logger LOG = LoggerFactory.getLogger(JsonSerialize.class);
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 对象序列化
     *
     * @param body Object
     * @return
     */
    public static byte[] serialize(Object body) {
        try {
            String jsonString = JSON.toJSONString(body);
            return jsonString.getBytes(Charset.forName(DEFAULT_CHARSET));
        } catch(Exception var2) {
            LOG.error(String.format("对象序列化 Error: %s", var2));
            return null;
        }
    }

    /**
     * 字符串序列化
     *
     * @param body String
     * @return
     */
    public static byte[] serialize(String body) {
        try {
            return body.getBytes(Charset.forName(DEFAULT_CHARSET));
        } catch(Exception var2) {
            LOG.error(String.format("对象序列化 Error: %s", var2));
            return null;
        }
    }

    /**
     * 字符串反序列化
     *
     * @param body
     * @return String
     */
    public static String deSerialize(byte[] body) {
        try {
            return new String(body, Charset.forName(DEFAULT_CHARSET));
        } catch(Exception var3) {
            LOG.error(String.format("Error reason: %s", var3));
            return null;
        }
    }

    /**
     * 对象反序列化
     *
     * @param body
     * @param clazz
     * @param <T>
     * @return Object
     */
    public static <T> T deSerialize(byte[] body, Class<T> clazz) {
        try {
            String msgBody = new String(body, Charset.forName(DEFAULT_CHARSET));
            return JSON.parseObject(msgBody, clazz);
        } catch(Exception e) {
            LOG.error("对象反序列化失败", e);
            return null;
        }
    }
}
