package org.motechproject.bookingapp.service;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motechproject.bookingapp.domain.Clinic;
import org.motechproject.bookingapp.domain.Screening;
import org.motechproject.bookingapp.domain.ScreeningDto;
import org.motechproject.bookingapp.domain.ScreeningStatus;
import org.motechproject.bookingapp.domain.Volunteer;
import org.motechproject.bookingapp.exception.LimitationExceededException;
import org.motechproject.bookingapp.helper.VisitLimitationHelper;
import org.motechproject.bookingapp.repository.ClinicDataService;
import org.motechproject.bookingapp.repository.ScreeningDataService;
import org.motechproject.bookingapp.repository.VolunteerDataService;
import org.motechproject.bookingapp.service.impl.ScreeningServiceImpl;
import org.motechproject.commons.date.model.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

public class ScreeningServiceTest {

    @InjectMocks
    private ScreeningService screeningService = new ScreeningServiceImpl();

    @Mock
    private ScreeningDataService screeningDataService;

    @Mock
    private VolunteerDataService volunteerDataService;

    @Mock
    private ClinicDataService clinicDataService;

    @Mock
    private VisitLimitationHelper visitLimitationHelper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Clinic clinic;

    @Before
    public void setUp() {
        initMocks(this);
        clinic = new Clinic("siteId", "location", 1, 5, 2, 9, 8, 7, 10, 10, 10, 10, 10, 10, 10);
        clinic.setId(1l);
    }

    @Test
    public void shouldThrowLimitationExceededExceptionWhenNumberOfRoomsLimitIsReached() {
        when(clinicDataService.findById(1l)).thenReturn(clinic);
        when(volunteerDataService.create(any(Volunteer.class))).thenReturn(new Volunteer());
        List<Screening> screenings = new ArrayList<>(Arrays.asList(
                createScreeningVisit(2l, clinic, new LocalDate(2217, 1, 1), new Time(11, 0), new Time(12, 0))
        ));

        when(screeningDataService.findByClinicIdAndDateAndStatus(1l, new LocalDate(2217, 1, 1), ScreeningStatus.ACTIVE)).thenReturn(screenings);

        ScreeningDto screeningDto = createScreeningDto("1", "1", "2217-1-1", "11:00");
        Screening screening = new Screening();
        screening.setId(1l);

        thrown.expect(LimitationExceededException.class);
        thrown.expectMessage("Too many visits at the same time");
        screeningService.add(screeningDto, false);

    }

    @Test
    public void shouldThrowLimitationExceededExceptionWhenTypeOfVisitLimitIsReached() {
        when(clinicDataService.findById(1l)).thenReturn(clinic);
        when(volunteerDataService.create(any(Volunteer.class))).thenReturn(new Volunteer());
        List<Screening> screenings = new ArrayList<>(Arrays.asList(
                createScreeningVisit(2l, clinic, new LocalDate(2217, 1, 1), new Time(10, 0), new Time(11, 0)),
                createScreeningVisit(3l, clinic, new LocalDate(2217, 1, 1), new Time(9, 0), new Time(10, 0))
        ));

        when(screeningDataService.findByClinicIdAndDateAndStatus(1l, new LocalDate(2217, 1, 1), ScreeningStatus.ACTIVE)).thenReturn(screenings);

        ScreeningDto screeningDto = createScreeningDto("1", "1", "2217-1-1", "12:00");
        Screening screening = new Screening();
        screening.setId(1l);

        thrown.expect(LimitationExceededException.class);
        thrown.expectMessage("The booking limit for this type of visit has been reached");
        screeningService.add(screeningDto, false);
    }

    @Test
    public void shouldCheckCapacityLimitFirst() {
        when(clinicDataService.findById(1l)).thenReturn(clinic);
        when(volunteerDataService.create(any(Volunteer.class))).thenReturn(new Volunteer());
        List<Screening> screenings = new ArrayList<>(Arrays.asList(
                createScreeningVisit(2l, clinic, new LocalDate(2217, 1, 1), new Time(10, 0), new Time(11, 0)),
                createScreeningVisit(3l, clinic, new LocalDate(2217, 1, 1), new Time(9, 0), new Time(10, 0))
        ));

        when(screeningDataService.findByClinicIdAndDateAndStatus(1l, new LocalDate(2217, 1, 1), ScreeningStatus.ACTIVE)).thenReturn(screenings);

        doThrow(new LimitationExceededException("The clinic capacity limit for this day has been reached")).when(visitLimitationHelper).checkCapacityForScreening(any(LocalDate.class), any(Clinic.class), anyLong());

        ScreeningDto screeningDto = createScreeningDto("1", "1", "2217-1-1", "12:00");
        Screening screening = new Screening();
        screening.setId(1l);

        thrown.expect(LimitationExceededException.class);
        thrown.expectMessage("The clinic capacity limit for this day has been reached");
        screeningService.add(screeningDto, false);
    }

    @Test
    public void shouldNotCheckLimitationWhenIgnoreLimitationVariableIsTrue() {
        when(clinicDataService.findById(1l)).thenReturn(clinic);
        when(volunteerDataService.create(any(Volunteer.class))).thenReturn(new Volunteer());
        List<Screening> screenings = new ArrayList<>(Arrays.asList(
                createScreeningVisit(2l, clinic, new LocalDate(2217, 1, 1), new Time(10, 0), new Time(11, 0)),
                createScreeningVisit(3l, clinic, new LocalDate(2217, 1, 1), new Time(9, 0), new Time(10, 0))
        ));

        when(screeningDataService.findByClinicIdAndDateAndStatus(1l, new LocalDate(2217, 1, 1), ScreeningStatus.ACTIVE)).thenReturn(screenings);

        ScreeningDto screeningDto = createScreeningDto("1", "1", "2217-1-1", "12:00");
        Screening screening = new Screening();
        screening.setId(1l);

        screeningService.add(screeningDto, true);

        verify(visitLimitationHelper, never()).checkCapacityForScreening(any(LocalDate.class), any(Clinic.class), anyLong());
        verify(screeningDataService, times(1)).create(any(Screening.class));
    }

    private Screening createScreeningVisit(Long id, Clinic clinic, LocalDate date, Time startTime, Time endTime) {
        Screening screening = new Screening();
        screening.setId(id);
        screening.setClinic(clinic);
        screening.setDate(date);
        screening.setStartTime(startTime);
        screening.setEndTime(endTime);
        return screening;
    }

    private ScreeningDto createScreeningDto(String id, String clinicId, String date, String startTime) {
        ScreeningDto screeningDto = new ScreeningDto();
        screeningDto.setId(id);
        screeningDto.setClinicId(clinicId);
        screeningDto.setDate(date);
        screeningDto.setStartTime(startTime);
        return screeningDto;
    }

}
