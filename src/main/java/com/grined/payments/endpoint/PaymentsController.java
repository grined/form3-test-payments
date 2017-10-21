package com.grined.payments.endpoint;

import com.grined.payments.entity.dto.EditPaymentDTO;
import com.grined.payments.entity.dto.PaymentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/payments")
public interface PaymentsController {
    @RequestMapping(method = RequestMethod.GET)
    List<PaymentDTO> list(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
    PaymentDTO findOne(@PathVariable String paymentId);

    @RequestMapping(method = RequestMethod.POST)
    PaymentDTO create(@RequestBody EditPaymentDTO editPaymentDTO);

    @RequestMapping(value = "/{paymentId}", method = RequestMethod.PUT)
    PaymentDTO update(@PathVariable String paymentId, @RequestBody EditPaymentDTO editPaymentDTO);

    @RequestMapping(value = "/{paymentId}", method = RequestMethod.DELETE)
    void delete(@PathVariable String paymentId);
}
