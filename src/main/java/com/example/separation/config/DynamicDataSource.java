package com.example.separation.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/6/18 15:13
 *
 * AbstractRoutingDataSource的作用是基于查找key路由到对应的数据源，
 * 它内部维护了一组目标数据源，
 * 并且做了路由key与目标数据源之间的映射，提供基于key查找数据源的方法
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /*@Override
    protected Object determineCurrentLookupKey() {
        return null;
    }*/

    /**
     * 在mapper访问数据库时要确定数据源，根据DataSourceContextHolder.get()获取到key值，确定数据源建立链接执行sql返回数据
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }
}
