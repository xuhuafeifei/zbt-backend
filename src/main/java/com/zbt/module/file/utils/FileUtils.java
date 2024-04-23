package com.zbt.module.file.utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 文件工具类
 */
public class FileUtils {
    /**
     * 常见图片后缀
     */
    private static final List<String> imgPrefix = Arrays.asList(".jpg", ".jpeg", ".png");

    /**
     * 获取后缀
     */
    public static String getFileSuffix(String fileName) {
        // 检查文件名是否为null或空
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        // 查找最后一个点（.）的位置
        int dotIndex = fileName.lastIndexOf('.');

        // 检查是否找到点，且不是在字符串开头
        if (dotIndex > 0) {
            // 从点开始截取，直到字符串末尾
            return fileName.substring(dotIndex);
        }

        // 如果没有找到点，或点在字符串开头，则返回空字符串
        return "";
    }

    /**
     * 获取后缀
     */
    public static String getFilePrefix(String fileName) {
        // 检查文件名是否为null或空
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        // 查找最后一个点（.）的位置
        int dotIndex = fileName.lastIndexOf('.');

        // 检查是否找到点，且不是在字符串开头
        if (dotIndex > 0) {
            // 截取到.
            return fileName.substring(0, dotIndex);
        }

        // 如果没有找到点，或点在字符串开头，则返回空字符串
        return "";
    }

    /**
     * example one for getFileName
     * 获取源文件名 + datetime + random number
     */
    public static Callable<String> getOriginFileNameWithDate(String originFileName) {
        return new Callable<String>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public String call() throws Exception {
                String prefix = getFilePrefix(originFileName);
                String suffix = getFileSuffix(originFileName);
                return prefix + "-" + UUID.randomUUID().toString().substring(0, 5) + suffix;
            }
        };
    }

    /**
     * example one for getFileName
     * 获取随机名字: randomName + original_prefix
     * 截取文件原本名字, 获取后缀; 并随机生成新文件名
     */
    public static Callable<String> getRandomFileName(String fileName) {
        return new Callable<String>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             */
            @Override
            public String call() {
                String fileSuffix = getFileSuffix(fileName);
                return UUID.randomUUID().toString().substring(0, 10) + fileSuffix;
            }
        };
    }

    public static String getFilenameFromURL(String url) {
        // 去除URL的协议（http, https, etc.）
        String filePath = url.split("://")[1];
        // 去除URL的主机部分（域名或IP地址）
        String fileName = filePath.split("/")[filePath.split("/").length - 1];
        // 返回文件名
        return fileName;
    }

}
