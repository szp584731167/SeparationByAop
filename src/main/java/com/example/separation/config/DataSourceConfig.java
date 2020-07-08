package com.example.separation.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.separation.dynamicdatasource.DynamicDataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/6/18 15:09
 */
@Configuration
@MapperScan(basePackages = "com.example.separation.mapper", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {

    // 主库
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDb() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 从库
     */
    @Bean
    /**
     *  数组，获取对应property名称的值，与name不可同时使用
     * 	String[] value() default {};
     *
     * 	配置属性名称的前缀，比如spring.http.encoding
     * 	String prefix() default "";
     *
     * 	数组，配置属性完整名称或部分名称
     *  可与prefix组合使用，组成完整的配置属性名称，与value不可同时使用
     * 	String[] name() default {};
     *
     * 	可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
     * 	String havingValue() default "";
     *
     * 	缺少该配置属性时是否可以加载。如果为true，没有该配置属性时也会正常加载；反之则不会生效
     * 	boolean matchIfMissing() default false;
     * 	目前试验：条件：配置文件没有配置slave,当matchIfMissing为false的时候可以加载，为true的时候不可以加载.这里不太明白
     *
     */
    @ConditionalOnProperty(prefix = "spring.datasource", name = "slave",matchIfMissing = true)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDb() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 主从动态配置
     * 配置默认数据库以及所有数据库
     */
    @Bean
    public DynamicDataSource dynamicDb(@Qualifier("masterDb") DataSource masterDataSource,
                                       @Autowired(required = false) @Qualifier("slaveDb") DataSource slaveDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
        if (slaveDataSource != null) {
            targetDataSources.put(DynamicDataSourceEnum.SLAVE.getDataSourceName(), slaveDataSource);
        }
        //这里将数据源防止容器中，方便后期路由。根据key获取对应的数据源
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    /**
     * 创建SqlSessionFactory
     * @param dynamicDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDb") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    /**
     * SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * DataSourceTransactionManager
     * @param dynamicDataSource
     * @return
     */
    @Bean(name = "dataSourceTx")
    public DataSourceTransactionManager dataSourceTx(@Qualifier("dynamicDb") DataSource dynamicDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }

}
