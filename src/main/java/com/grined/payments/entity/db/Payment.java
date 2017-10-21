package com.grined.payments.entity.db;

import com.grined.payments.entity.Attributes;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter @Setter
public class Payment extends BasicDocument {
    private String organisationId;
    private Attributes attributes;
}
