package com.grined.payments.endpoint.impl;

import com.grined.payments.endpoint.PaymentsController;
import com.grined.payments.entity.DTOConverter;
import com.grined.payments.entity.db.Payment;
import com.grined.payments.entity.dto.EditPaymentDTO;
import com.grined.payments.entity.dto.PaymentDTO;
import com.grined.payments.exception.ConflictException;
import com.grined.payments.exception.NotFoundException;
import com.grined.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PaymentsControllerImpl implements PaymentsController{
    private final PaymentService paymentService;

    @Autowired
    public PaymentsControllerImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public List<PaymentDTO> list(Integer page, Integer size) {
        Stream<Payment> paymentStream;
        if (page == null && size == null) {
            paymentStream = paymentService.findAll();
        } else if (page == null || size == null) { // one of parameter is unspecified
            throw new ConflictException("To paginate your request both page and size parameters should be specified");
        } else {
            paymentStream = paymentService.findPage(page, size).getContent().stream();
        }
        return paymentStream
                .filter(Objects::nonNull)
                .map(DTOConverter::paymentToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO findOne(@PathVariable String paymentId) {
        if (StringUtils.isEmpty(paymentId)) {
            throw new NotFoundException("Can not find payment resource for null or empty id");
        }
        return paymentService.findPaymentById(paymentId)
                .map(DTOConverter::paymentToDTO)
                .orElseThrow(() -> new NotFoundException("Can not find payment resource for id: " + paymentId));
    }

    @Override
    public PaymentDTO create(@RequestBody EditPaymentDTO editPaymentDTO) {
        if (editPaymentDTO == null) {
            throw new ConflictException("Illegal request body : null");
        }
        return DTOConverter.paymentToDTO(paymentService.addPayment(editPaymentDTO));
    }

    @Override
    public PaymentDTO update(@PathVariable String paymentId, @RequestBody EditPaymentDTO editPaymentDTO) {
        if (StringUtils.isEmpty(paymentId)) {
            throw new NotFoundException("Can not find payment resource for null or empty id");
        }
        if (editPaymentDTO == null) {
            throw new ConflictException("Illegal request body : null");
        }
        return DTOConverter.paymentToDTO(paymentService.updatePayment(paymentId, editPaymentDTO));
    }

    @Override
    public void delete(@PathVariable String paymentId) {
        if (StringUtils.isEmpty(paymentId)) {
            throw new NotFoundException("Can not find payment resource for null or empty id");
        }
        paymentService.deletePayment(paymentId);
    }
}
