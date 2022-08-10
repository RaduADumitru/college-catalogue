package interfaces;

import enums.AuditOption;
import enums.AuditUtilClass;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;

public interface AuditableUtil {
    static void auditUtil(AuditOption option, AuditUtilClass staticOption) {
    final HashSet<AuditOption> auditOptions;
    switch (staticOption) {
        case AUX -> auditOptions = new HashSet<>(
                Arrays.asList(
                        AuditOption.AUX_ADD_COURSE,
                        AuditOption.AUX_ADD_GRADE,
                        AuditOption.AUX_ADD_GROUP,
                        AuditOption.AUX_ADD_SERIES,
                        AuditOption.AUX_ADD_PROFESSOR,
                        AuditOption.AUX_ADD_GROUP_TO_SERIES,
                        AuditOption.AUX_ADD_SPECIALIZATION,
                        AuditOption.AUX_ADD_STUDENT,
                        AuditOption.AUX_ADD_SUBJECT,
                        AuditOption.AUX_GET_COURSES,
                        AuditOption.AUX_GET_GRADES,
                        AuditOption.AUX_GET_GROUPS,
                        AuditOption.AUX_GET_SERIES,
                        AuditOption.AUX_GET_PROFESSORS,
                        AuditOption.AUX_GET_SPECIALIZATIONS,
                        AuditOption.AUX_GET_STUDENTS,
                        AuditOption.AUX_GET_SUBJECTS,
                        AuditOption.AUX_ADD_GROUP_TO_SERIES,
                        AuditOption.AUX_LIST_STUDENT,
                        AuditOption.AUX_LIST_STUDENTS_IN_UNIT,
                        AuditOption.AUX_SET_REPRESENTATIVE,
                        AuditOption.AUX_SEARCH_COURSE,
                        AuditOption.AUX_SEARCH_PROFESSOR,
                        AuditOption.AUX_SEARCH_SERIES,
                        AuditOption.AUX_SEARCH_SPECIALIZATION,
                        AuditOption.AUX_SEARCH_STUDENT,
                        AuditOption.AUX_SEARCH_SUBJECT,
                        AuditOption.AUX_SEARCH_YEAR
                )
        );
        case SERVICE -> auditOptions = new HashSet<>(
                Arrays.asList(
                        AuditOption.SERVICE_LOAD_DATA,
                        AuditOption.SERVICE_ADD_COURSE_TO_PROFESSOR,
                        AuditOption.SERVICE_ADD_GRADE_TO_STUDENT,
                        AuditOption.SERVICE_ADD_STUDENT,
                        AuditOption.SERVICE_LIST_STUDENT,
                        AuditOption.SERVICE_ADD_SPECIALIZATION_TO_STUDENT,
                        AuditOption.SERVICE_LIST_COURSES_OF_SUBJECT,
                        AuditOption.SERVICE_LIST_STUDENTS_IN_UNIT,
                        AuditOption.SERVICE_SET_REPRESENTATIVE,
                        AuditOption.SERVICE_SHOW_AVERAGE_IN_SERIES,
                        AuditOption.SERVICE_SHOW_YEARLY_AVERAGE
                )
        );
        default -> auditOptions = new HashSet<>();
    }
    if(auditOptions.contains(option)) {
        Auditer.log(staticOption.getAuditClass() + ":" + option.getAction());
    }
    else {
        Auditer.log(staticOption.getAuditClass() + ":Undefined Action");
    }
}
}
