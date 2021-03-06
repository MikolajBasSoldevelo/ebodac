package org.motechproject.ebodac.service;


import org.motechproject.ebodac.web.domain.GridSettings;
import org.motechproject.ebodac.web.domain.Records;
import org.motechproject.mds.dto.LookupDto;
import org.motechproject.mds.query.QueryParams;

import java.util.List;

public interface LookupService {

    <T> List<T> getEntities(Class<T> entityType, GridSettings settings, QueryParams queryParams);

    <T> Records<T> getEntities(Class<T> entityType, String lookup,
                               String lookupFields, QueryParams queryParams);

    <T> Records<?> getEntities(Class<?> entityDtoType, Class<T> entityType, String lookup,
                               String lookupFields, QueryParams queryParams);

    List<LookupDto> getAvailableLookups(String entityName);
}
