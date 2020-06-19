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

    DynamicDataSourceEnum value() default DynamicDataSourceEnum.MASTER;
    boolean clear() default true;
}
