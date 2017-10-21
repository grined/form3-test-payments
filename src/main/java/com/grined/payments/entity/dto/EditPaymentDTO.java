package com.grined.payments.entity.dto;

import com.grined.payments.entity.Attributes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPaymentDTO {
    private String organisationId;
    private Attributes attributes;
}
