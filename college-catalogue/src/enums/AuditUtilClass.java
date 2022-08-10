package enums;

public enum AuditUtilClass {
    AUX("AuxService"),
    SERVICE("Service");
    private final String auditClass;

    AuditUtilClass(String auditClass) {
        this.auditClass = auditClass;
    }

    public String getAuditClass() {
        return auditClass;
    }
}
