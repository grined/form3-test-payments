package com.grined.payments.service.impl;

import com.grined.payments.dao.PaymentRepository;
import com.grined.payments.entity.db.Payment;
import com.grined.payments.entity.dto.EditPaymentDTO;
import com.grined.payments.exception.ConflictException;
import com.grined.payments.exception.NotFoundException;
import com.grined.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Stream<Payment> findAll() {
        return paymentRepository.streamByDeletedFalse();
    }

    @Override
    public Page<Payment> findPage(int page, int size) {
        if (page < 0 || size < 1) {
            throw new ConflictException("Page value should be non negative. Page size should be positive.");
        }
        return paymentRepository.findByDeletedFalse(new PageRequest(page, size));
    }

    @Override
    public Optional<Payment> findPaymentById(String id) {
        Payment payment = paymentRepository.findOne(id);
        if (payment == null || payment.isDeleted()) {
            return Optional.empty();
        }
        return Optional.of(payment);
    }

    @Override
    public Payment addPayment(EditPaymentDTO editPaymentDTO) {
        if (editPaymentDTO == null) {
            throw new ConflictException("Can not add null payment");
        }
        Payment payment = new Payment();
        payment.setVersion(0);
        payment.setDeleted(false);
        payment.setOrganisationId(editPaymentDTO.getOrganisationId());
        payment.setAttributes(editPaymentDTO.getAttributes());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(String id, EditPaymentDTO editPaymentDTO) {
        if (editPaymentDTO == null) {
            throw new ConflictException("Can not edit null payment");
        }
        Payment payment = findPaymentById(id)
                .orElseThrow(() -> new NotFoundException("Can not find payment for update"));
        payment.setVersion(payment.getVersion() + 1);
        payment.setOrganisationId(editPaymentDTO.getOrganisationId());
        payment.setAttributes(editPaymentDTO.getAttributes());
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(String id) {
        Optional<Payment> paymentById = findPaymentById(id);
        Payment payment = paymentById
                .orElseThrow(() -> new ConflictException("Payment has been already deleted or not found"));
        paymentRepository.delete(payment.getId());
    }
}
