package com.immutable.visitormanagement.response;

import com.immutable.visitormanagement.dto.PersonalAndOfficialByOrganization;
import com.immutable.visitormanagement.dto.VisitorDto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardResponse {
    private Long totalVisitorCount;
    private Long totalActiveAccount;
    private Double busiestHours;
    private Map<String,Double> pieChartData;
    private Map<String,Long>   barGraphData;
    private List<PersonalAndOfficialByOrganization> personalAndOfficialByOrganization;
    private List<DownloadResponse> visitorDto;
}
