package enums;

public enum Degree {
    BACHELOR(1),
    MASTER(2),
    PHD(3);
    private final int order;
    Degree(int order){
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
