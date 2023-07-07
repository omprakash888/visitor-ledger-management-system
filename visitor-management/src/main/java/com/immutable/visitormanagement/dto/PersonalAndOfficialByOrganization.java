package com.immutable.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalAndOfficialByOrganization {

    private String organizationName;
    private long personalVisitCount;
    private long officialVisitCount;
}
