package interfaces;

import enums.AuditOption;
import service.Auditer;

public interface Auditable {
    default void audit(AuditOption option) {
        Auditer.log(getClass().getSimpleName() + ":Audit not defined!");
    }
}
