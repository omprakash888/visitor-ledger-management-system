package com.immutable.visitormanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardRequest {

    private String day;
    private String month;
    private String year;
    private String organizationName;
}
