package org.motechproject.ebodac.repository;

import org.joda.time.LocalDate;
import org.motechproject.commons.api.Range;
import org.motechproject.ebodac.domain.Subject;
import org.motechproject.ebodac.domain.VisitType;
import org.motechproject.mds.annotations.Lookup;
import org.motechproject.mds.annotations.LookupField;
import org.motechproject.mds.service.MotechDataService;
import org.motechproject.mds.util.Constants;

import java.util.List;

/**
 * Interface for repository that persists simple records and allows CRUD.
 * MotechDataService base class will provide the implementation of this class as well
 * as methods for adding, deleting, saving and finding all instances.  In this class we
 * define and custom lookups we may need.
 */
public interface SubjectDataService extends MotechDataService<Subject> {

    @Lookup(name = "Find By Name")
    List<Subject> findByName(@LookupField(name = "name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name);

    @Lookup(name = "Find unique By Participant Id")
    Subject findBySubjectId(@LookupField(name = "subjectId") String subjectId);

    @Lookup(name = "Find By Modified")
    List<Subject> findByModified(@LookupField(name = "changed") Boolean modified);

    @Lookup(name = "Find By Primer Vaccination Date Range")
    List<Subject> findByPrimerVaccinationDateRange(@LookupField(name = "primerVaccinationDate")
                                                   Range<LocalDate> dateRange);

    @Lookup(name = "Find By Booster Vaccination Date Range")
    List<Subject> findByBoosterVaccinationDateRange(@LookupField(name = "boosterVaccinationDate")
                                                    Range<LocalDate> dateRange);

    @Lookup(name = "Find By Primer Vaccination Date")
    List<Subject> findByPrimerVaccinationDate(@LookupField(name = "primerVaccinationDate") LocalDate dateRange);

    @Lookup(name = "Find By Booster Vaccination Date")
    List<Subject> findByBoosterVaccinationDate(@LookupField(name = "boosterVaccinationDate") LocalDate dateRange);

    @Lookup(name = "Find By Address")
    List<Subject> findByAddress(@LookupField(name = "address",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String address);

    @Lookup(name = "Find By Participant Id")
    List<Subject> findByMatchesCaseInsensitiveSubjectId(@LookupField(name = "subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId);

    @Lookup(name = "Find By exact Phone Number")
    List<Subject> findByPhoneNumber(@LookupField(name = "phoneNumber") String phoneNumber);

    @Lookup(name = "Find By Visit Type and Actual Date")
    List<Subject> findByVisitTypeAndActualDate(@LookupField(name = "visits.type") VisitType visitType,
                                               @LookupField(name = "visits.date", customOperator = Constants.Operators.NEQ) LocalDate date);

    @Lookup(name = "Find By Stage Id")
    List<Subject> findByStageId(@LookupField(name = "stageId") Long stageId);

    /**
     * UI Lookups
     */
    @Lookup(name = "Find By Name")
    List<Subject> findByNameAndModificationDate(@LookupField(name = "name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find unique By Participant Id")
    Subject findBySubjectIdAndModificationDate(@LookupField(name = "subjectId") String subjectId,
                                               @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Modified")
    List<Subject> findByModifiedAndModificationDate(@LookupField(name = "changed") Boolean modified,
                                                    @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Primer Vaccination Date Range")
    List<Subject> findByPrimerVaccinationDateRangeAndModificationDate(@LookupField(name = "primerVaccinationDate")
                                                   Range<LocalDate> dateRange,
                                                                      @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Booster Vaccination Date Range")
    List<Subject> findByBoosterVaccinationDateRangeAndModificationDate(@LookupField(name = "boosterVaccinationDate")
                                                    Range<LocalDate> dateRange,
                                                                       @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Primer Vaccination Date")
    List<Subject> findByPrimerVaccinationDateAndModificationDate(@LookupField(name = "primerVaccinationDate") LocalDate dateRange,
                                                                 @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Booster Vaccination Date")
    List<Subject> findByBoosterVaccinationDateAndModificationDate(@LookupField(name = "boosterVaccinationDate") LocalDate dateRange,
                                                                  @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Address")
    List<Subject> findByAddressAndModificationDate(@LookupField(name = "address",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String address,
                                                   @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Participant Id")
    List<Subject> findByMatchesCaseInsensitiveSubjectIdAndModificationDate(@LookupField(name = "subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId,
                                                                           @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By exact Phone Number")
    List<Subject> findByPhoneNumberAndModificationDate(@LookupField(name = "phoneNumber") String phoneNumber,
                                                       @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Visit Type and Actual Date")
    List<Subject> findByVisitTypeAndActualDateAndModificationDate(@LookupField(name = "visits.type") VisitType visitType,
                                               @LookupField(name = "visits.date", customOperator = Constants.Operators.NEQ) LocalDate date,
                                                                  @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);

    @Lookup(name = "Find By Stage Id")
    List<Subject> findByStageIdAndModificationDate(@LookupField(name = "stageId") Long stageId,
                                                   @LookupField(name = "modificationDate") Range<LocalDate> modificationDate);


}
