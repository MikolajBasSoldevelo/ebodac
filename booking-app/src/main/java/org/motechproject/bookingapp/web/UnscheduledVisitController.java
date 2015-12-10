package org.motechproject.bookingapp.web;

import org.motechproject.bookingapp.constants.BookingAppConstants;
import org.motechproject.bookingapp.domain.UnscheduledVisit;
import org.motechproject.bookingapp.domain.UnscheduledVisitDto;
import org.motechproject.bookingapp.exception.LimitationExceededException;
import org.motechproject.bookingapp.helper.DtoLookupHelper;
import org.motechproject.bookingapp.service.UnscheduledVisitService;
import org.motechproject.bookingapp.web.domain.BookingGridSettings;
import org.motechproject.ebodac.exception.EbodacLookupException;
import org.motechproject.ebodac.repository.SubjectDataService;
import org.motechproject.ebodac.service.LookupService;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.dto.LookupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("unscheduledVisits")
@PreAuthorize(BookingAppConstants.HAS_UNSCHEDULED_VISITS_TAB_ROLE)
public class UnscheduledVisitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnscheduledVisitController.class);

    @Autowired
    private LookupService lookupService;

    @Autowired
    private UnscheduledVisitService unscheduledVisitService;

    @Autowired
    private SubjectDataService subjectDataService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Records<UnscheduledVisitDto> getUnscheduledVisits(BookingGridSettings settings) throws IOException {
        return unscheduledVisitService.getUnscheduledVisitsRecords(DtoLookupHelper.changeLookupForScreeningAndUnscheduled(settings));
    }

    @RequestMapping(value = "/new/{ignoreLimitation}", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Object addOrUpdateUnscheduled(@PathVariable Boolean ignoreLimitation,
                                         @RequestBody UnscheduledVisitDto unscheduledVisitDto) {
        try {
            return unscheduledVisitService.addOrUpdate(unscheduledVisitDto, ignoreLimitation);
        } catch (LimitationExceededException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/getLookupsForUnscheduled", method = RequestMethod.GET)
    @ResponseBody
    public List<LookupDto> getLookupsForUnscheduled() {
        List<LookupDto> ret = new ArrayList<>();
        List<LookupDto> availableLookups;
        try {
            availableLookups = lookupService.getAvailableLookups(UnscheduledVisit.class.getName());
        } catch (EbodacLookupException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
        List<String> lookupList = BookingAppConstants.AVAILABLE_LOOKUPS_FOR_UNSCHEDULED;
        for (LookupDto lookupDto : availableLookups) {
            if (lookupList.contains(lookupDto.getLookupName())) {
                ret.add(lookupDto);
            }
        }
        return ret;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return e.getMessage();
    }
}
