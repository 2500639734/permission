package com.permission.common;

import lombok.Builder;
import lombok.Data;

/**
 * @auther: shenke
 * @date: 2020/3/8 19:48
 * @description: 便捷返回多值
 */
@Data
@Builder
public class DoubleResult <T, S> {

    /**
     * 返回值类型1
     */
    private T t;

    /**
     * 返回值类型2
     */
    private S s;

    /**
     * 构建DoubleResult
     * @param t 返回值1
     * @param s 返回值2
     * @param <T> 返回值1类型
     * @param <S> 返回值2类型
     * @return
     */
    public static <T, S> DoubleResult<T, S> build (T t, S s) {
        return new DoubleResult<>(t, s);
    }

}
