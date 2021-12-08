package com.zhonghaiwenda.gaea.common.mongodb.tool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class ParamUtils {

    private final static Set<Character> META_CHAR = new HashSet<>();

    static {
        META_CHAR.add('$');
        META_CHAR.add('(');
        META_CHAR.add(')');
        META_CHAR.add('[');
        META_CHAR.add(']');
        META_CHAR.add('{');
        META_CHAR.add('}');
        META_CHAR.add('*');
        META_CHAR.add('+');
        META_CHAR.add('.');
        META_CHAR.add('?');
        META_CHAR.add('/');
    }


    /**
     * 把字符串中的元字符转义
     *
     * @param resource 元字符
     * @return 转义之后的字符
     **/
    public static String replaceAllMateChar(String resource) {
        if (resource == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        char[] chars = resource.toCharArray();
        for (char aChar : chars) {
            if (META_CHAR.contains(aChar)) {
                sb.append("\\");
            }
            sb.append(aChar);
        }
        return sb.toString();
    }

}
