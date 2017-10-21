package com.grined.payments.entity.dto;

import com.grined.payments.entity.Attributes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDTO {
    private String id;
    private int version;
    private String organizationId;
    private Attributes attributes;
}
