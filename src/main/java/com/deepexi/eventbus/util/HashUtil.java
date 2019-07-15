package com.deepexi.eventbus.util;

import java.util.zip.CRC32;

/**
 *
 * @Author: zhangtao
 * @Date: 2019/4/12 15:38
 */
public class HashUtil {

    /**
     * crc32
     * @param bytes
     * @return
     */
    public static long crc32Code(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return crc32.getValue();
    }
}
