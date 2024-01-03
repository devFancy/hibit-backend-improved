package com.hibitbackendrefactor.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_date_time", nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @LastModifiedDate
    @Column(name = "updated_date_time", nullable = false)
    private LocalDateTime updateDateTime;

    public LocalDateTime getCreatedAt() {
        return createDateTime;
    }

    public LocalDateTime getUpdatedAt() {
        return updateDateTime;
    }
}
