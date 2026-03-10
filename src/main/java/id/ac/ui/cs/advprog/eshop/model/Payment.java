package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    private final String[] VALID_STATUSES = {"WAITING", "SUCCESS", "REJECTED"};

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;

        this.setStatus(status);

        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (Arrays.asList(VALID_STATUSES).contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}