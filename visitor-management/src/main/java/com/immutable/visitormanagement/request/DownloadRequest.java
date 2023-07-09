package com.immutable.visitormanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadRequest {
    private String organizationName;
    private String typeOfVisit;
    private String startDate;
    private String endDate;
}
