package com.tuniu.bi.umsj.base.config;

import com.tuniu.bi.umsj.base.annotation.DevMagicMapper;
import com.tuniu.bi.umsj.base.annotation.FlushCmMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 集群数据源配置
 *
 * @author zhangwei21
 */
@Configuration
@MapperScan(basePackages = {"com.tuniu.bi.umsj"}, sqlSessionFactoryRef = "devMagicSqlSessionFactory", annotationClass = DevMagicMapper.class)
public class DevMagicDataSourceConfig {

    /**
     * 创建数据源
     *
     * @return
     */
    @Bean(name = "devMagicDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dev-magic")
    public DataSource getDateSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 事务管理
     *
     * @param dataSource
     * @return
     * @throws SQLException
     */
    @Bean("devMagicTransactionManager")
    public DataSourceTransactionManager devMagicTransactionManager(@Qualifier("devMagicDataSource") DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }
    /**
     * 创建sqlSessionFactory
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "devMagicSqlSessionFactory")
    public SqlSessionFactory devMagicSqlSessionFactory(@Qualifier("devMagicDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/***/mapper/*.xml"));
        return bean.getObject();
    }

    /**
     * 创建SqlSessionTemplate
     *
     * @param sessionFactory
     * @return
     */
    @Bean("devMagicSqlSessionTemplate")
    public SqlSessionTemplate devMagicSqlSessionTemplate(
            @Qualifier("devMagicSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
