package com.zhonghaiwenda.gaea.framework.core.tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;


class FileUtilsTest {

    @Test
    @DisplayName("测试工具类创建文件")
    public void buildFileTest(){
        File file = new File(new File("aaa"),"bbb"+File.separator+"ccc");
        System.out.println(file.getAbsolutePath());
        Assertions.assertFalse(file.exists());
        FileUtils.buildFiles(file);
        Assertions.assertTrue(file.exists());
        FileUtils.del(file);
        FileUtils.del(file.getParentFile());
        FileUtils.del(file.getParentFile().getParentFile());
        Assertions.assertFalse(file.exists());
    }

}
