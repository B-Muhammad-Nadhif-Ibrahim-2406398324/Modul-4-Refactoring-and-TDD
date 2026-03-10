package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, String status, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.setStatus(status);

        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.paymentData = paymentData;
        this.order = order;
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}