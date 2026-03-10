package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        this.paymentData.clear();
        String paymentId = UUID.randomUUID().toString();

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), this.paymentData);
        });
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.WAITING.getValue(), this.paymentData);

        assertEquals(paymentId, payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(PaymentStatus.WAITING.getValue(), payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentRejectedStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.REJECTED.getValue(), this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        String paymentId = UUID.randomUUID().toString();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(paymentId, "VOUCHER", "MEOW", this.paymentData);
        });
    }

    @Test
    void testSetStatusToSuccess() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.WAITING.getValue(), this.paymentData);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.WAITING.getValue(), this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}