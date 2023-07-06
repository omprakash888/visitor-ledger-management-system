package com.immutable.visitormanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardResponse {

    private Map<String,Double> pieChartData;
}
