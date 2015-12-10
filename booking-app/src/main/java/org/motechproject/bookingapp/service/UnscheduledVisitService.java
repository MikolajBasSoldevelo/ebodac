package org.motechproject.bookingapp.service;


import org.motechproject.bookingapp.domain.UnscheduledVisitDto;
import org.motechproject.bookingapp.web.domain.BookingGridSettings;
import org.motechproject.ebodac.web.domain.Records;

import java.io.IOException;

public interface UnscheduledVisitService {

    Records<UnscheduledVisitDto> getUnscheduledVisitsRecords(BookingGridSettings settings) throws IOException;

    UnscheduledVisitDto addOrUpdate(UnscheduledVisitDto unscheduledVisitDto, Boolean ignoreLimitation);
}
