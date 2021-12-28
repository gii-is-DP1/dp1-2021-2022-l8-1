package org.springframework.samples.SevenIslands.model;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity {
    
    @CreatedBy
    protected String CreatedBy;

    @CreatedDate
    protected Date createdDate;

    @LastModifiedBy
    protected String lastModifiedBy;

    @LastModifiedDate
    private Date lastModifiedDate;

}
