package id.ac.ui.cs.advprog.eshop.model;

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
            new Payment(paymentId, "VOUCHER", "SUCCESS", this.paymentData);
        });
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", "WAITING", this.paymentData);

        assertEquals(paymentId, payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("WAITING", payment.getStatus());
        assertSame(this.paymentData, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", "SUCCESS", this.paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentRejectedStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", "REJECTED", this.paymentData);
        assertEquals("REJECTED", payment.getStatus());
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
        Payment payment = new Payment(paymentId, "VOUCHER", "WAITING", this.paymentData);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, "VOUCHER", "WAITING", this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}