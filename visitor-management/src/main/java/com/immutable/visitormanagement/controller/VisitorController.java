package com.immutable.visitormanagement.controller;

import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.request.DashboardRequest;
import com.immutable.visitormanagement.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.immutable.visitormanagement.constants.Constants.DASHBOARD_URL_VISITOR;

@RestController
public class VisitorController {

    private final VisitorService visitorServices;

    @Autowired
    public VisitorController(VisitorService visitorServices) {
        this.visitorServices = visitorServices;
    }


    @PostMapping("/create")
    public ResponseEntity<VisitorDto> createVisitorEntry(@Valid @RequestBody VisitorDto visitorDto) {
        VisitorDto visitorDto1 = visitorServices.save(visitorDto);
        return new ResponseEntity<>(visitorDto1,HttpStatus.CREATED);
    }

    @GetMapping("/getAllVisitors")
    public ResponseEntity<List<VisitorDto>> getAllVisitors() {
        List<VisitorDto> visitorDtos = this.visitorServices.getAllVisitors();
        return new ResponseEntity<>(visitorDtos, HttpStatus.OK);
    }

    @GetMapping("/getVisitorById")
    public ResponseEntity<VisitorDto> getVistorById(@RequestParam("id") Long visitorId) {
        VisitorDto visitorDto = this.visitorServices.getVisitorById(visitorId);
        return new ResponseEntity<>(visitorDto,HttpStatus.OK);
    }

    @PostMapping("/updateOutTime")
    public ResponseEntity<String> updateOutTime(@RequestParam("visitorId") Long visitorId) {
        String response = this.visitorServices.updateOutTime(visitorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DASHBOARD_URL_VISITOR)
    public ResponseEntity<Map<String,Long>> getDashboardData(@RequestBody DashboardRequest dashboardRequest) {

       // Map<String,Double> pieChartData = this.visitorServices.getPieChartData(dashboardRequest);
        Map<String,Long> barGraph = this.visitorServices.getBarGraphData(dashboardRequest);

        return new ResponseEntity<>(barGraph,HttpStatus.OK);
    }

}
