package com.grined.payments.entity.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class BasicDocument {
    @Id
    protected String id;
    protected int version;
    protected boolean deleted;
}
