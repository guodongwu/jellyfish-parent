package com.jellyfish.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.jellyfish.ds.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置
 * 由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
 */
@Configuration
@MapperScan(basePackages ="com.jellyfish.mapper")
@EnableTransactionManagement(order = 2)
@ComponentScan({"com.jellyfish.config"})
public class DataSourceConfig {
    /**
     * ================================================================
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    @Bean(name = "mysqlDataSource")
    public DruidDataSource mysqlDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    @Bean(name = "oracleDataSource")
    public DruidDataSource oracleDataSource(){
        return DruidDataSourceBuilder.create().build();
    }




    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "datasource", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return this.oracleDataSource();
    }


    /**
     * 多数据源连接池配置
     * ConditionalOnProperty注解用来控制多数据源下DataSourceAspect切面生效
     */
    @Bean(value = "dynamicDataSource")
    @Primary
    @ConditionalOnProperty(prefix = "datasource", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {
        // mysql数据源
        DruidDataSource mysqlDataSource = this.mysqlDataSource();
        // oracle数据源
        DruidDataSource oracleDataSource = this.oracleDataSource();

        try {
            mysqlDataSource.init();
            oracleDataSource.init();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> hashMap = new HashMap();
        hashMap.put("mysqlDataSource", mysqlDataSource);
        hashMap.put("oracleDataSource", oracleDataSource);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(oracleDataSource);
        return dynamicDataSource;
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean(name = "sqlSessionFactory")
    @ConfigurationProperties(prefix = "mybatis-plus")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(value = "dynamicDataSource") DynamicDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }
    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate oracleJdbcTemplate(){
        JdbcTemplate jdbcTemplate=new JdbcTemplate(this.oracleDataSource());
        return  jdbcTemplate;
    }
    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(){
        JdbcTemplate jdbcTemplate=new JdbcTemplate(this.mysqlDataSource());
        return  jdbcTemplate;
    }

}
