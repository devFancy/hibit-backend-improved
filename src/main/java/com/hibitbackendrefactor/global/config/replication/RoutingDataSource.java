package com.hibitbackendrefactor.global.config.replication;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        System.out.println("Transaction의 Read Only가 " + isReadOnly + " 입니다.");

        if(isReadOnly) {
            System.out.println("Replica 서버로 요청합니다.");
            return "replica";
        }
        System.out.println("Source 서버로 요청합니다.");
        return "source";
    }
}
