package com.immutable.visitormanagement.service;


import com.immutable.visitormanagement.dto.PersonalAndOfficialByOrganization;
import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.request.DashboardRequest;
import com.immutable.visitormanagement.request.DownloadRequest;
import com.immutable.visitormanagement.response.DownloadResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public interface VisitorService {

    VisitorDto save(VisitorDto visitorDto);

    List<DownloadResponse> getAllVisitors();


    VisitorDto getVisitorById(Long visitorId);

    String updateOutTime(Long visitorId);

    Map<String, Double> getPieChartData(DashboardRequest dashboardRequest);

    Map<String, Long> getBarGraphData(DashboardRequest dashboardRequest);

    List<PersonalAndOfficialByOrganization> getPersonalAndOfficialByOrganization(DashboardRequest dashboardRequest);

    long getActiveVisitors();

    Double getBusiestCheckInTime();

    List<String> getAllVisitorOrganization();

    List<DownloadResponse> downloadData(DownloadRequest downloadRequest);
}
