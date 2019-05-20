package com.mye.mine.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DatasourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceTypeManager.get();
    }
}
