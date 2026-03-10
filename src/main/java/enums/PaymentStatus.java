package enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    WAITING("WAITING"),
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getValue().equals(param)) {
                return true;
            }
        }
        return false;
    }
}