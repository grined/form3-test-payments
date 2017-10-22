package com.grined.payments.service;

import com.grined.payments.entity.db.Payment;
import com.grined.payments.entity.dto.EditPaymentDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.stream.Stream;

public interface PaymentService {
    Stream<Payment> findAll();

    Stream<Payment> findByOrganisationId(String organisationId);

    Page<Payment> findAllPaged(int page, int size);

    Page<Payment> findByOrganisationIdPaged(String organisationId, Integer page, Integer size);

    Optional<Payment> findPaymentById(String id);

    Payment addPayment(EditPaymentDTO editPaymentDTO);

    Payment updatePayment(String id, EditPaymentDTO editPaymentDTO);

    void deletePayment(String id);
}
