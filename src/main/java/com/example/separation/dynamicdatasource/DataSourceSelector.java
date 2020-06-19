package com.example.separation.dynamicdatasource;

import com.example.separation.dynamicdatasource.DynamicDataSourceEnum;

import java.lang.annotation.*;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/6/18 17:03
 * 为了可以方便切换数据源，我们可以写一个注解，注解中包含数据源对应的枚举值，默认是主库
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSelector {

    /**
     * 根据注解选择数据源
     * @return
     */
    DynamicDataSourceEnum value() default DynamicDataSourceEnum.MASTER;

    /**
     * 默认为true,用在清除threadLocal,以防止在多线程情况下内存泄漏
     * @return
     */
    boolean clear() default true;
}
