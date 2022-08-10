package enums;

public enum Year implements Comparable<Year>{
    B1(1, Degree.BACHELOR),
    B2(2, Degree.BACHELOR),
    B3(3, Degree.BACHELOR),
    B4(4, Degree.BACHELOR),
    M1(1, Degree.MASTER),
    M2(2, Degree.MASTER),
    P1(1, Degree.PHD),
    P2(2, Degree.PHD);
    private Integer year;
    private Degree degree;
private Year(int year, Degree degree){
    this.year=year;
    this.degree=degree;
}

}
