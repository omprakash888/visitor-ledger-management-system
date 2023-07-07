package com.immutable.visitormanagement.controller;

import com.immutable.visitormanagement.dto.PersonalAndOfficialByOrganization;
import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.request.DashboardRequest;
import com.immutable.visitormanagement.response.DashBoardResponse;
import com.immutable.visitormanagement.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.immutable.visitormanagement.constants.Constants.*;

@RestController
public class VisitorController {

    private final VisitorService visitorServices;


    @Autowired
    public VisitorController(VisitorService visitorServices) {
        this.visitorServices = visitorServices;
    }


    @PostMapping(CREATE_URL_VISITOR)
    public ResponseEntity<VisitorDto> createVisitorEntry(@Valid @RequestBody VisitorDto visitorDto) {
        VisitorDto visitorDto1 = visitorServices.save(visitorDto);
        return new ResponseEntity<>(visitorDto1,HttpStatus.CREATED);
    }

    @GetMapping(GET_ALL_URL_VISITOR)
    public ResponseEntity<List<VisitorDto>> getAllVisitors() {
        List<VisitorDto> visitorDtos = this.visitorServices.getAllVisitors();
        return new ResponseEntity<>(visitorDtos, HttpStatus.OK);
    }

    @GetMapping(GET_BY_ID_URL_VISITOR)
    public ResponseEntity<VisitorDto> getVistorById(@RequestParam("id") Long visitorId) {
        VisitorDto visitorDto = this.visitorServices.getVisitorById(visitorId);
        return new ResponseEntity<>(visitorDto,HttpStatus.OK);
    }

    @PostMapping(CHECKOUT_URL_VISITOR)
    public ResponseEntity<String> updateOutTime(@RequestParam("visitorId") Long visitorId) {
        String response = this.visitorServices.updateOutTime(visitorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DASHBOARD_URL_VISITOR)
    public ResponseEntity<DashBoardResponse> getDashboardData(@RequestBody DashboardRequest dashboardRequest) {

        Map<String,Double> pieChartData = this.visitorServices.getPieChartData(dashboardRequest);
        Map<String,Long> barGraphData = this.visitorServices.getBarGraphData(dashboardRequest);
        List<PersonalAndOfficialByOrganization> personalAndOfficialByOrganizationData = this.visitorServices.getPersonalAndOfficialByOrganization(dashboardRequest);
        List<VisitorDto> visitorDto = this.visitorServices.getAllVisitors();
        long totalVisitorCount = visitorDto.size();
        long countOfActiveVisitor = this.visitorServices.getActiveVisitors();
        Double busiestHours = this.visitorServices.getBusiestCheckInTime();

        DashBoardResponse response = DashBoardResponse.builder()
                .totalVisitorCount(totalVisitorCount)
                .totalActiveAccount(countOfActiveVisitor)
                .busiestHours(busiestHours)
                .pieChartData(pieChartData)
                .barGraphData(barGraphData)
                .personalAndOfficialByOrganization(personalAndOfficialByOrganizationData)
                .visitorDto(visitorDto)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
