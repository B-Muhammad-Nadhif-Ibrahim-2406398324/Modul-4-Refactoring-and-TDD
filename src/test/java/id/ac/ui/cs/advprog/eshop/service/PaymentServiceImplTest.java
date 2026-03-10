package id.ac.ui.cs.advprog.eshop.service;

import enums.OrderStatus;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Order> orders;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        this.orders = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("product-1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);
        products.add(product);

        Order order1 = new Order(UUID.randomUUID().toString(), products, 1708560000L, "Safira Sudrajat");
        this.orders.add(order1);

        this.payments = new ArrayList<>();
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        doReturn(null).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentVoucherRejected() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123");

        doReturn(null).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Order order = orders.get(0);
        Payment payment = new Payment(UUID.randomUUID().toString(), "VOUCHER",
                PaymentStatus.WAITING.getValue(), paymentData, order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejectedUpdatesOrderToFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Order order = orders.get(0);
        Payment payment = new Payment(UUID.randomUUID().toString(), "VOUCHER",
                PaymentStatus.WAITING.getValue(), paymentData, order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testAddPaymentBankTransferSuccess() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");

        doReturn(null).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferRejectedEmptyBankName() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");

        doReturn(null).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferRejectedEmptyReferenceCode() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");

        doReturn(null).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }
}