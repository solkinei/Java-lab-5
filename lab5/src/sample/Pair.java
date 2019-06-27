package sample;

public class Pair {
    private Integer sum;
    private String message;

    public Pair(Integer sum, String message) {
        this.sum = sum;
        this.message = message;
    }

    public Integer getSum() {
        return sum;
    }

    public String getMessage() {
        return message;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
