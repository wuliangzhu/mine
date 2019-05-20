package com.mye.mine.config;


import com.mye.mine.entity.DataSources;

/**
 * 数据源管理类
 */
public class DataSourceTypeManager {

    /**
     * 数据源线程池
     */
    private static final ThreadLocal<DataSources> dataSourceTypes = new ThreadLocal<DataSources>() {
        @Override
        protected DataSources initialValue() {
            return DataSources.EYuyan;
        }
    };

    /**
     * 获取当前线程数据源
     * @return
     */
    public static DataSources get() {

        return dataSourceTypes.get();
    }

    /**
     * 设置当前线程数据源
     * @param dataSourceType
     */
    public static void set(DataSources dataSourceType) {

        dataSourceTypes.set(dataSourceType);
    }

    /**
     * 重置当前线程数据源
     */
    public static void reset() {

        dataSourceTypes.set(DataSources.EYuyan);
    }

    /**
     * 删除当前线程数据源变量
     */
    public static void remove() {

        dataSourceTypes.remove();
    }
}