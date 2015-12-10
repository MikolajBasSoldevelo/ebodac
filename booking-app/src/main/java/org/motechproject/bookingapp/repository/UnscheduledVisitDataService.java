package org.motechproject.bookingapp.repository;

import org.joda.time.LocalDate;
import org.motechproject.bookingapp.domain.UnscheduledVisit;
import org.motechproject.commons.api.Range;
import org.motechproject.mds.annotations.Lookup;
import org.motechproject.mds.annotations.LookupField;
import org.motechproject.mds.service.MotechDataService;
import org.motechproject.mds.util.Constants;

import java.util.List;


public interface UnscheduledVisitDataService extends MotechDataService<UnscheduledVisit> {

    @Lookup
    List<UnscheduledVisit> findByClinicLocation(@LookupField(name = "clinic.location",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String location);

    @Lookup
    List<UnscheduledVisit> findByClinicLocationAndDate(@LookupField(name = "date") Range<LocalDate> date,
                                                @LookupField(name = "clinic.location",
                                                        customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String location);

    @Lookup
    List<UnscheduledVisit> findByClinicIdAndDate(@LookupField(name = "date", customOperator = Constants.Operators.EQ) LocalDate date,
                                                       @LookupField(name = "clinic.id") Long id);

    @Lookup
    List<UnscheduledVisit> findByDate(@LookupField(name = "date") Range<LocalDate> dateRange);

    @Lookup
    List<UnscheduledVisit> findByParticipantId(@LookupField(name = "subject.subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId);

    @Lookup
    List<UnscheduledVisit> findByParticipantIdAndDate(@LookupField(name = "date") Range<LocalDate> date,
                                                      @LookupField(name = "subject.subjectId", customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId);
}
