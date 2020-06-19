package com.example.separation.dynamicdatasource;

import lombok.Getter;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/6/18 15:07
 */
@Getter
public enum DynamicDataSourceEnum {
    /**
     *
     */
    MASTER("master"),
    /**
     *
     */
    SLAVE("slave");
    private String dataSourceName;
    DynamicDataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
