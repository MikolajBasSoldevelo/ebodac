package org.motechproject.bookingapp.service.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.LocalDate;
import org.motechproject.bookingapp.domain.Clinic;
import org.motechproject.bookingapp.domain.VisitBookingDetails;
import org.motechproject.bookingapp.domain.VisitRescheduleDto;
import org.motechproject.bookingapp.domain.VisitScheduleOffset;
import org.motechproject.bookingapp.exception.LimitationExceededException;
import org.motechproject.bookingapp.repository.ClinicDataService;
import org.motechproject.bookingapp.repository.ScreeningDataService;
import org.motechproject.bookingapp.repository.UnscheduledVisitDataService;
import org.motechproject.bookingapp.repository.VisitBookingDetailsDataService;
import org.motechproject.bookingapp.service.VisitRescheduleService;
import org.motechproject.bookingapp.service.VisitScheduleOffsetService;
import org.motechproject.bookingapp.web.domain.BookingGridSettings;
import org.motechproject.commons.date.model.Time;
import org.motechproject.ebodac.domain.Visit;
import org.motechproject.ebodac.domain.VisitType;
import org.motechproject.ebodac.repository.VisitDataService;
import org.motechproject.ebodac.service.EbodacEnrollmentService;
import org.motechproject.ebodac.service.LookupService;
import org.motechproject.ebodac.util.QueryParamsBuilder;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.query.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("visitRescheduleService")
public class VisitRescheduleServiceImpl implements VisitRescheduleService {

    @Autowired
    private LookupService lookupService;

    @Autowired
    private EbodacEnrollmentService ebodacEnrollmentService;

    @Autowired
    private VisitBookingDetailsDataService visitBookingDetailsDataService;

    @Autowired
    private ScreeningDataService screeningDataService;

    @Autowired
    private UnscheduledVisitDataService unscheduledVisitDataService;

    @Autowired
    private VisitDataService visitDataService;

    @Autowired
    private ClinicDataService clinicDataService;

    @Autowired
    private VisitScheduleOffsetService visitScheduleOffsetService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Records<VisitRescheduleDto> getVisitsRecords(BookingGridSettings settings) throws IOException {
        QueryParams queryParams = QueryParamsBuilder.buildQueryParams(settings, getFields(settings.getFields()));
        Records<VisitBookingDetails> detailsRecords = lookupService.getEntities(VisitBookingDetails.class, settings.getLookup(), settings.getFields(), queryParams);

        Map<VisitType, VisitScheduleOffset> offsetMap = visitScheduleOffsetService.getAllAsMap();

        List<VisitRescheduleDto> dtos = new ArrayList<>();

        for (VisitBookingDetails details: detailsRecords.getRows()) {
            dtos.add(new VisitRescheduleDto(details, offsetMap));
        }

        return new Records<>(detailsRecords.getPage(), detailsRecords.getTotal(), detailsRecords.getRecords(), dtos);
    }

    @Override
    public VisitRescheduleDto saveVisitReschedule(VisitRescheduleDto visitRescheduleDto, Boolean ignoreLimitation) {
        VisitBookingDetails visitBookingDetails = visitBookingDetailsDataService.findById(visitRescheduleDto.getVisitBookingDetailsId());

        if (visitBookingDetails == null) {
            throw new IllegalArgumentException("Cannot reschedule, because details for Visit not found");
        }

        Clinic clinic = clinicDataService.findById(visitRescheduleDto.getClinicId());

        if (!ignoreLimitation) {
            checkNumberOfPatients(visitRescheduleDto, clinic);
        }

        Visit visit = visitBookingDetails.getVisit();
        validateDate(visitRescheduleDto, visit);
        updateVisitPlannedDate(visit, visitRescheduleDto);

        return new VisitRescheduleDto(updateVisitDetailsWithDto(visitBookingDetails, visitRescheduleDto, clinic));
    }

    private void checkNumberOfPatients(VisitRescheduleDto dto, Clinic clinic) {

        List<VisitBookingDetails> visits = visitBookingDetailsDataService
                .findByClinicIdVisitPlannedDateAndType(dto.getClinicId(), dto.getPlannedDate(), dto.getVisitType());

        if (visits != null && !visits.isEmpty()) {
            int numberOfRooms = clinic.getNumberOfRooms();
            int maxVisits = clinic.getMaxPrimeVisits();
            int patients = 0;

            for (VisitBookingDetails visit : visits) {
                if (visit.getId().equals(dto.getVisitBookingDetailsId())) {
                    maxVisits++;
                } else {
                    Time startTime = dto.getStartTime();
                    Time endTime = dto.getEndTime();

                    if (startTime.isBefore(visit.getStartTime())) {
                        if (visit.getStartTime().isBefore(endTime)) {
                            patients++;
                        }
                    } else {
                        if (startTime.isBefore(visit.getEndTime())) {
                            patients++;
                        }
                    }
                }
            }

            if (visits.size() >= maxVisits) {
                throw new LimitationExceededException("Maximum amount of Prime Vaccination Visits exceeded for this day");
            }
            if (patients >= numberOfRooms) {
                throw new LimitationExceededException("Too many Patients at the same time");
            }
        }
        checkCapacity(dto.getPlannedDate(), clinic);
    }

    private void validateDate(VisitRescheduleDto dto, Visit visit) {
        if (visit.getDate() != null) {
            throw new IllegalArgumentException("Cannot reschedule, because Visit already took place");
        }

        if (dto.getPlannedDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }

        Map<VisitType, VisitScheduleOffset> offsetMap = visitScheduleOffsetService.getAllAsMap();

        LocalDate primeVacDate = visit.getSubject().getPrimerVaccinationDate();

        LocalDate earliestDate = primeVacDate.plusDays(offsetMap.get(dto.getVisitType()).getEarliestDateOffset());
        LocalDate latestDate = primeVacDate.plusDays(offsetMap.get(dto.getVisitType()).getLatestDateOffset());

        if (dto.getPlannedDate().isBefore(earliestDate) || dto.getPlannedDate().isAfter(latestDate)) {
            throw new IllegalArgumentException(String.format("The date should be between %s and %s but is %s",
                    earliestDate, latestDate, dto.getPlannedDate()));
        }
    }

    private VisitBookingDetails updateVisitDetailsWithDto(VisitBookingDetails details, VisitRescheduleDto dto, Clinic clinic) {
        details.setStartTime(dto.getStartTime());
        details.setEndTime(dto.getEndTime());
        details.setClinic(clinic);
        return visitBookingDetailsDataService.update(details);
    }

    private Visit updateVisitPlannedDate(Visit visit, VisitRescheduleDto visitRescheduleDto) {
        visit.setMotechProjectedDate(visitRescheduleDto.getPlannedDate());

        if (ebodacEnrollmentService.checkIfEnrolledAndUpdateEnrollment(visit)) {
            ebodacEnrollmentService.reenrollSubject(visit);
        }

        return visitDataService.update(visit);
    }

    private Map<String, Object> getFields(String json) throws IOException {
        if (json == null) {
            return null;
        } else {
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap>() {});  //NO CHECKSTYLE WhitespaceAround
        }
    }

    private void checkCapacity(LocalDate date, Clinic clinic) {
        if(clinic != null && date != null) {
            int screeningCount = screeningDataService.findByDateAndClinicId(date, clinic.getId()).size();
            int unscheduledVisitCount = unscheduledVisitDataService.findByClinicIdAndDate(date, clinic.getId()).size();
            int visitBookingDetailsCount = visitBookingDetailsDataService.findByBookingPlannedDateAndClinicId(date, clinic.getId()).size();
            int visitCount = screeningCount + visitBookingDetailsCount + unscheduledVisitCount;
            if (visitCount >= clinic.getMaxCapacityByDay()) {
                throw new LimitationExceededException("The limit of the capacity by day in the clinic is reached");
            }
        }
    }
}
