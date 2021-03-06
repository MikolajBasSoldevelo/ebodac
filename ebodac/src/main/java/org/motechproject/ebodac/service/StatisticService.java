package org.motechproject.ebodac.service;

import org.joda.time.DateTime;
import org.motechproject.commons.api.Range;
import org.motechproject.ebodac.domain.IvrEngagementStatistic;
import org.motechproject.ebodac.domain.IvrAndSmsStatistic;

import java.util.List;

public interface StatisticService {

    IvrAndSmsStatistic getStatisticForIvr(Range<DateTime> dateRange);

    IvrAndSmsStatistic getStatisticForSms(Range<DateTime> dateRange);

    List<IvrEngagementStatistic> getIvrEngagementStatistic();
}
