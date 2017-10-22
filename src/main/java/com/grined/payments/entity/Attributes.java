package com.grined.payments.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
public class Attributes {
    private Double amount;
    private String currency;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate processingDate;
    private String paymentId;
    private String paymentPurpose;
    private String paymentType;
    private BeneficiaryParty beneficiaryParty;
}
