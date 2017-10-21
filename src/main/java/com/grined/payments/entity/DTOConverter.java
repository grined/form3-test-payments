package com.grined.payments.entity;

import com.grined.payments.entity.db.Payment;
import com.grined.payments.entity.dto.PaymentDTO;

public final class DTOConverter {
    private DTOConverter(){}

    public static PaymentDTO paymentToDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentDTO.builder()
                .id(payment.getId())
                .attributes(payment.getAttributes())
                .organizationId(payment.getOrganisationId())
                .version(payment.getVersion())
                .build();
    }
}
