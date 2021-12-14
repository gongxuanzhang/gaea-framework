package com.zhonghaiwenda.gaea.framework.core.tool;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * 文件相关工具类
 *
 * @author gongxuanzhang
 */
@Slf4j
public class FileUtils extends FileUtil {

    public final static int KB = 1024;
    public final static int MB = 1024 * KB;
    public final static long GB = 1024 * MB;
    public final static long TB = 1024 * GB;


    private FileUtils() {
        throw new RuntimeException("禁止创建实体");
    }


    public static boolean buildDirs(BiConsumer<File, Boolean> consumer, File... files) {
        boolean result = true;
        for (File file : files) {
            boolean successBuild = file.mkdirs();
            if (consumer != null) {
                consumer.accept(file, successBuild);
            }
            result &= successBuild;
        }
        return result;
    }


    public static boolean buildDirs(File... files) {
        return buildDirs(null, files);
    }

    public static boolean buildDirs(String ... paths){
        return buildDirs(null,paths);
    }


    public static boolean buildDirs(BiConsumer<File, Boolean> consumer, String... paths) {
        if (paths != null) {
            File[] files = new File[paths.length];
            for (int i = 0; i < files.length; i++) {
                files[i] = new File(paths[i]);
            }
            return buildDirs(consumer, files);
        }
        return false;
    }

    public static boolean buildDirs(BiConsumer<File, Boolean> consumer, Collection<String> paths) {
        if (!CollectionUtils.isEmpty(paths)) {
            return buildDirs(consumer, paths.toArray(new String[]{}));
        }
        return false;

    }

    public static boolean buildDirs(Collection<String> paths) {
        return buildDirs(null,paths);
    }


    /**
     * 如果指定文件未存在 创建出所有指定文件
     *
     * @param consumer 每个创建行为的回调函数 <File,Boolean> 是文件和是否创建成功
     * @param files    多个等待创建的文件
     * @return 是否所有文件都创建成功
     **/
    public static boolean buildFiles(BiConsumer<File, Boolean> consumer, File... files) {
        boolean result = true;
        for (File file : files) {
            boolean successBuild = buildFile(file);
            if (consumer != null) {
                consumer.accept(file, successBuild);
            }
            result &= successBuild;
        }
        return result;
    }

    public static boolean buildFiles(File... files) {
        return buildFiles(null, files);
    }

    public static boolean buildFiles(BiConsumer<File, Boolean> consumer, String... paths) {
        boolean result = true;
        for (String path : paths) {
            File file = new File(path);
            boolean successBuild = buildFile(file);
            if (consumer != null) {
                consumer.accept(file, successBuild);
            }
            result &= successBuild;
        }
        return result;
    }


    public static boolean buildFiles(String... paths) {
        return buildFiles(null, paths);
    }

    public static boolean buildFiles(Collection<File> files) {
        if (!CollectionUtils.isEmpty(files)) {
            return buildFiles(files.toArray(new File[]{}));
        }
        return false;
    }

    /**
     * 如果文件不存在 则创建
     *
     * @param file 目标文件
     * @return 创建是否成功
     **/
    public static boolean buildFile(File file) {
        if (file == null || file.exists()) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


}
