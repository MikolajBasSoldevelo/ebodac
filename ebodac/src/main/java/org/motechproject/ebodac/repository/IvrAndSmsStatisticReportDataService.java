package org.motechproject.ebodac.repository;

import org.joda.time.DateTime;
import org.motechproject.commons.api.Range;
import org.motechproject.ebodac.domain.IvrAndSmsStatisticReport;
import org.motechproject.ebodac.domain.SmsStatus;
import org.motechproject.mds.annotations.Lookup;
import org.motechproject.mds.annotations.LookupField;
import org.motechproject.mds.service.MotechDataService;
import org.motechproject.mds.util.Constants;

import java.util.List;
import java.util.Set;

public interface IvrAndSmsStatisticReportDataService extends MotechDataService<IvrAndSmsStatisticReport> {

    @Lookup(name = "Find By Participant Id")
    List<IvrAndSmsStatisticReport> findBySubjectId(@LookupField(name = "subject.subjectId",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String subjectId);

    @Lookup(name = "Find By Participant Phone Number")
    List<IvrAndSmsStatisticReport> findBySubjectPhoneNumber(@LookupField(name = "subject.phoneNumber",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String phoneNumber);

    @Lookup(name = "Find By Participant Community")
    List<IvrAndSmsStatisticReport> findBySubjectCommunity(@LookupField(name = "subject.community",
            customOperator = Constants.Operators.MATCHES_CASE_INSENSITIVE) String community);

    @Lookup
    List<IvrAndSmsStatisticReport> findBySentDate(@LookupField(name = "sendDate") Range<DateTime> sendDate);

    @Lookup
    List<IvrAndSmsStatisticReport> findByReceivedDate(@LookupField(name = "receivedDate") Range<DateTime> receivedDate);

    @Lookup
    List<IvrAndSmsStatisticReport> findByProviderCallId(@LookupField(name = "providerCallId") String providerCallId);

    @Lookup(name = "Find By ProviderCallId And Participant Id")
    IvrAndSmsStatisticReport findByProviderCallIdAndSubjectId(@LookupField(name = "providerCallId") String providerCallId,
                                                              @LookupField(name = "subject.subjectId") String subjectId);

    @Lookup(name = "Find By SMS Status")
    List<IvrAndSmsStatisticReport> findBySmsStatus(@LookupField(name = "smsStatus") Set<SmsStatus> smsStatus);

    @Lookup(name = "Find By SMS Status And Sent Date")
    List<IvrAndSmsStatisticReport> findBySmsStatusAndSentDate(@LookupField(name = "smsStatus") Set<SmsStatus> smsStatus,
                                                              @LookupField(name = "sendDate") Range<DateTime> sendDate);
}
