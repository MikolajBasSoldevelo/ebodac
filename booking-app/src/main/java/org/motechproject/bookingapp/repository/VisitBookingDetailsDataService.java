package org.motechproject.bookingapp.repository;

import org.joda.time.LocalDate;
import org.motechproject.bookingapp.domain.VisitBookingDetails;
import org.motechproject.commons.api.Range;
import org.motechproject.ebodac.domain.VisitType;
import org.motechproject.mds.annotations.Lookup;
import org.motechproject.mds.annotations.LookupField;
import org.motechproject.mds.service.MotechDataService;
import org.motechproject.mds.util.Constants;

import java.util.List;
import java.util.Set;

public interface VisitBookingDetailsDataService extends MotechDataService<VisitBookingDetails> {

    @Lookup(name = "Find By exact Participant Id")
    List<VisitBookingDetails> findBySubjectId(@LookupField(name = "subject.subjectId") String subjectId);

    @Lookup
    VisitBookingDetails findByVisitId(@LookupField(name = "visit.id") Long visitId);

    @Lookup
    VisitBookingDetails findByParticipantIdAndVisitType(@LookupField(name = "subject.subjectId") String subjectId,
                                                        @LookupField(name = "visit.type") VisitType type);

    @Lookup
    List<VisitBookingDetails> findByBookingPlannedDateClinicIdAndVisitType(@LookupField(name = "bookingPlannedDate", customOperator = Constants.Operators.EQ) LocalDate bookingPlannedDate,
                                                                           @LookupField(name = "clinic.id") Long clinicId,
                                                                           @LookupField(name = "visit.type") VisitType type);

    @Lookup
    List<VisitBookingDetails> findByBookingPlannedDateAndClinicId(@LookupField(name = "bookingPlannedDate", customOperator = Constants.Operators.EQ) LocalDate bookingPlannedDate,
                                                                           @LookupField(name = "clinic.id") Long clinicId);

    @Lookup
    List<VisitBookingDetails> findByClinicIdVisitPlannedDateAndType(@LookupField(name = "clinic.id") Long clinicId,
                                                                    @LookupField(name = "visit.motechProjectedDate") LocalDate plannedDate,
                                                                    @LookupField(name = "visit.type") VisitType type);

    @Lookup
    List<VisitBookingDetails> findByParticipantNamePrimeVaccinationDateAndVisitTypeAndBookingPlannedDateEq(@LookupField(name = "subject.name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                                                                           @LookupField(name = "subject.primerVaccinationDate",
                                                                                                                   customOperator = Constants.Operators.EQ) LocalDate primerVaccinationDate,
                                                                                                           @LookupField(name = "visit.type") VisitType visitType,
                                                                                                           @LookupField(name = "bookingPlannedDate",
                                                                                                                   customOperator = Constants.Operators.EQ) LocalDate bookingPlannedDate);

    /**
     *  UI Lookups
     */

    @Lookup
    List<VisitBookingDetails> findByParticipantId(@LookupField(name = "subject.subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId);

    @Lookup
    List<VisitBookingDetails> findByParticipantName(@LookupField(name = "subject.name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name);

    @Lookup
    List<VisitBookingDetails> findByVisitType(@LookupField(name = "visit.type") VisitType type);

    @Lookup
    List<VisitBookingDetails> findByClinicLocation(@LookupField(name = "clinic.location",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String location);

    @Lookup
    List<VisitBookingDetails> findByVisitActualDate(@LookupField(name = "visit.date") LocalDate date);

    @Lookup
    List<VisitBookingDetails> findByVisitActualDateRange(@LookupField(name = "visit.date") Range<LocalDate> date);

    @Lookup
    List<VisitBookingDetails> findByVisitPlannedDate(@LookupField(name = "visit.motechProjectedDate") LocalDate date);

    @Lookup
    List<VisitBookingDetails> findByVisitPlannedDateRange(@LookupField(name = "visit.motechProjectedDate") Range<LocalDate> date);

    @Lookup
    List<VisitBookingDetails> findByVisitTypeAndParticipantPrimeVaccinationDateAndName(@LookupField(name = "visit.type") VisitType type,
                                                                                       @LookupField(name = "subject.primerVaccinationDate",
                                                                                               customOperator = Constants.Operators.EQ) LocalDate primerVaccinationDate,
                                                                                       @LookupField(name = "subject.name",
                                                                                               customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name);

    /**
     *  Prime Vaccination Screen Lookups
     */

    @Lookup
    List<VisitBookingDetails> findByParticipantNamePrimeVaccinationDateAndVisitTypeAndBookingPlannedDate(@LookupField(name = "subject.name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                                                                         @LookupField(name = "subject.primerVaccinationDate",
                                                                                                                 customOperator = Constants.Operators.EQ) LocalDate primerVaccinationDate,
                                                                                                         @LookupField(name = "visit.type") VisitType visitType,
                                                                                                         @LookupField(name = "bookingPlannedDate",
                                                                                                                 customOperator = Constants.Operators.NEQ) LocalDate bookingPlannedDate);

    @Lookup
    List<VisitBookingDetails> findByParticipantIdVisitTypeAndParticipantPrimeVaccinationDateAndNameAndBookingPlannedDate(@LookupField(name = "subject.subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId,
                                                                                                                         @LookupField(name = "visit.type") VisitType visitType,
                                                                                                                         @LookupField(name = "subject.primerVaccinationDate",
                                                                                                                                 customOperator = Constants.Operators.EQ) LocalDate primerVaccinationDate,
                                                                                                                         @LookupField(name = "subject.name",
                                                                                                                                 customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                                                                                         @LookupField(name = "bookingPlannedDate",
                                                                                                                                 customOperator = Constants.Operators.NEQ) LocalDate bookingPlannedDate);

    @Lookup
    List<VisitBookingDetails> findByParticipantNamePrimeVaccinationDateAndVisitTypeAndBookingPlannedDateRange(@LookupField(name = "subject.name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                                                                              @LookupField(name = "subject.primerVaccinationDate",
                                                                                                                      customOperator = Constants.Operators.EQ) LocalDate primerVaccinationDate,
                                                                                                              @LookupField(name = "visit.type") VisitType visitType,
                                                                                                              @LookupField(name = "bookingPlannedDate") Range<LocalDate> bookingPlannedDate);

    @Lookup
    List<VisitBookingDetails> findByParticipantIdVisitTypeAndParticipantPrimeVaccinationDateAndNameAndBookingPlannedDateRange(@LookupField(name = "subject.subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId,
                                                                                                                              @LookupField(name = "visit.type") VisitType visitType,
                                                                                                                              @LookupField(name = "subject.primerVaccinationDate",
                                                                                                                                      customOperator = Constants.Operators.EQ) LocalDate primerVaccinationDate,
                                                                                                                              @LookupField(name = "subject.name",
                                                                                                                                      customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                                                                                              @LookupField(name = "bookingPlannedDate") Range<LocalDate> bookingPlannedDate);

    /**
     *  Reschedule Screen Lookups
     */

    @Lookup
    List<VisitBookingDetails> findByPlannedDateAndVisitTypeSet(@LookupField(name = "visit.motechProjectedDate",
            customOperator = Constants.Operators.NEQ) LocalDate plannedDate,
                                                               @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByParticipantIdAndPlannedDateAndVisitTypeSet(@LookupField(name = "subject.subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId,
                                                                               @LookupField(name = "visit.motechProjectedDate",
                                                                                       customOperator = Constants.Operators.NEQ) LocalDate plannedDate,
                                                                               @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByParticipantNameAndPlannedDateAndVisitTypeSet(@LookupField(name = "subject.name",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String name,
                                                                                 @LookupField(name = "visit.motechProjectedDate",
                                                                                         customOperator = Constants.Operators.NEQ) LocalDate plannedDate,
                                                                                 @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByVisitTypeAndPlannedDate(@LookupField(name = "visit.type") VisitType type,
                                                            @LookupField(name = "visit.motechProjectedDate",
                                                                    customOperator = Constants.Operators.NEQ) LocalDate plannedDate);

    @Lookup
    List<VisitBookingDetails> findByClinicLocationAndPlannedDateAndVisitTypeSet(@LookupField(name = "clinic.location",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String location,
                                                                                @LookupField(name = "visit.motechProjectedDate",
                                                                                        customOperator = Constants.Operators.NEQ) LocalDate plannedDate,
                                                                                @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByVisitActualDateAndPlannedDateAndVisitTypeSet(@LookupField(name = "visit.date") LocalDate date,
                                                                                 @LookupField(name = "visit.motechProjectedDate",
                                                                                         customOperator = Constants.Operators.NEQ) LocalDate plannedDate,
                                                                                 @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByVisitActualDateRangeAndPlannedDateAndVisitTypeSet(@LookupField(name = "visit.date") Range<LocalDate> date,
                                                                                      @LookupField(name = "visit.motechProjectedDate",
                                                                                              customOperator = Constants.Operators.NEQ) LocalDate plannedDate,
                                                                                      @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByVisitPlannedDateAndVisitTypeSet(@LookupField(name = "visit.motechProjectedDate") LocalDate date,
                                                                    @LookupField(name = "visit.type") Set<VisitType> typeSet);

    @Lookup
    List<VisitBookingDetails> findByVisitPlannedDateRangeAndVisitTypeSet(@LookupField(name = "visit.motechProjectedDate") Range<LocalDate> date,
                                                                         @LookupField(name = "visit.type") Set<VisitType> typeSet);
}
