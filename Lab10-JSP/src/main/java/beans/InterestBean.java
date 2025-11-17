package beans;

public class InterestBean {
    private double principal;
    private double rate;
    private double time;

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getSimpleInterest() {
        return (principal * rate * time) / 100;
    }
}
