package com.grined.payments.endpoint;

import com.grined.payments.endpoint.impl.PaymentsControllerImpl;
import com.grined.payments.entity.db.Payment;
import com.grined.payments.entity.dto.EditPaymentDTO;
import com.grined.payments.exception.ConflictException;
import com.grined.payments.exception.NotFoundException;
import com.grined.payments.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentsControllerImplTest {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentsControllerImpl paymentsController;

    @Before
    public void init() {
    }

    @Test
    public void testThatGettingAllPaymentsReturnedEmptyOnEmptyRequest() {
        when(paymentService.findAll()).thenReturn(Stream.empty());
        assertEquals(0, paymentsController.list(null, null).size());
    }

    @Test(expected = ConflictException.class)
    public void testThatGettingAllPaymentsThrowsOnIllegalPageParametersSize() {
        paymentsController.list(1, null);
    }

    @Test(expected = ConflictException.class)
    public void testThatGettingAllPaymentsThrowsOnIllegalPageParametersPageNumber() {
        paymentsController.list(null, 1);
    }

    @Test
    public void testThatGettingAllPaymentsCallNecessaryServiceMethod() {
        when(paymentService.findAllPaged(anyInt(), anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        paymentsController.list(1, 2);
        verify(paymentService, times(1)).findAllPaged(1, 2);
    }

    @Test
    public void testThatGettingAllByOrganizationPaymentsReturnedEmptyOnEmptyRequest() {
        when(paymentService.findByOrganisationId(anyString())).thenReturn(Stream.empty());
        assertEquals(0, paymentsController.listByOrganization("123", null, null).size());
    }

    @Test(expected = ConflictException.class)
    public void testThatGettingAllByOrganizationPaymentsThrowsOnIllegalPageParametersSize() {
        paymentsController.listByOrganization("1", 1, null);
    }

    @Test(expected = ConflictException.class)
    public void testThatGettingAllByOrganizationPaymentsThrowsOnIllegalPageParametersPageNumber() {
        paymentsController.listByOrganization("1", null, 1);
    }

    @Test(expected = NotFoundException.class)
    public void testThatGettingAllByOrganizationPaymentsThrowsOnIllegalOrganizationId() {
        paymentsController.listByOrganization("", 1, 1);
    }

    @Test
    public void testThatGettingAllByOrganizationPaymentsCallNecessaryServiceMethod() {
        when(paymentService.findByOrganisationIdPaged(anyString(), anyInt(), anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        paymentsController.listByOrganization("abc", 1, 2);
        verify(paymentService, times(1)).findByOrganisationIdPaged("abc", 1, 2);
    }

    @Test
    public void testThatFindOneReturnedData() {
        Payment payment = new Payment();
        payment.setId("123");

        when(paymentService.findPaymentById(anyString())).thenReturn(Optional.of(payment));
        assertEquals("123", paymentsController.findOne("123").getId());
    }

    @Test(expected = NotFoundException.class)
    public void testThatFindOneThrowsOnEmptyId() {
        paymentsController.findOne("");
    }

    @Test(expected = NotFoundException.class)
    public void testThatFindOneThrowsOnEmptyResultFromDatabase() {
        when(paymentService.findPaymentById(anyString())).thenReturn(Optional.empty());
        paymentsController.findOne("123");
    }

    @Test
    public void testThatCreatePaymentCallsNecessaryServiceMethod() {
        when(paymentService.addPayment(anyObject())).thenReturn(new Payment());
        EditPaymentDTO editPaymentDTO = new EditPaymentDTO();
        paymentsController.create(editPaymentDTO);
        verify(paymentService, times(1)).addPayment(editPaymentDTO);
    }

    @Test(expected = ConflictException.class)
    public void testThatCreatePaymentThrowsOnNullBody() {
        paymentsController.create(null);
    }

    @Test
    public void testThatUpdatePaymentCallsNecessaryServiceMethod() {
        when(paymentService.updatePayment(anyString(), anyObject())).thenReturn(new Payment());
        EditPaymentDTO editPaymentDTO = new EditPaymentDTO();
        paymentsController.update("1", editPaymentDTO);
        verify(paymentService, times(1)).updatePayment("1", editPaymentDTO);
    }

    @Test(expected = ConflictException.class)
    public void testThatUpdatePaymentThrowsOnNullBody() {
        paymentsController.update("1", null);
    }

    @Test(expected = NotFoundException.class)
    public void testThatUpdatePaymentThrowsOnEmptyOrganisation() {
        paymentsController.update("", new EditPaymentDTO());
    }


    @Test
    public void testThatDeletePaymentCallsNecessaryServiceMethod() {
        paymentsController.delete("1");
        verify(paymentService, times(1)).deletePayment("1");
    }

    @Test(expected = NotFoundException.class)
    public void testThatDeletePaymentThrowsOnEmptyOrganisation() {
        paymentsController.delete("");
    }
}
