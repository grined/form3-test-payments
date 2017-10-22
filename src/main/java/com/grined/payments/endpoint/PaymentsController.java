package com.grined.payments.endpoint;

import com.grined.payments.entity.dto.EditPaymentDTO;
import com.grined.payments.entity.dto.PaymentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Payment resources service API", tags = "payments", description = "Fetch a payment resource, " +
        "Create update and delete a payment resource, " +
        "List a collection of payment resources")
@RequestMapping("/api/payments")
public interface PaymentsController {
    @ApiOperation(value = "List all payments resources")
    @RequestMapping(method = RequestMethod.GET)
    List<PaymentDTO> list(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @ApiOperation(value = "List payments resources for organisation")
    @RequestMapping(value = "/by-organisation/{organisationId}", method = RequestMethod.GET)
    List<PaymentDTO> listByOrganization(
            @PathVariable String organisationId,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @ApiOperation(value = "Find payment resource by id")
    @RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
    PaymentDTO findOne(@PathVariable String paymentId);

    @ApiOperation(value = "Create new payment resource")
    @RequestMapping(method = RequestMethod.POST)
    PaymentDTO create(@RequestBody EditPaymentDTO editPaymentDTO);

    @ApiOperation(value = "Update existing resource by id")
    @RequestMapping(value = "/{paymentId}", method = RequestMethod.PUT)
    PaymentDTO update(@PathVariable String paymentId, @RequestBody EditPaymentDTO editPaymentDTO);

    @ApiOperation(value = "Delete resource by id")
    @RequestMapping(value = "/{paymentId}", method = RequestMethod.DELETE)
    void delete(@PathVariable String paymentId);
}
