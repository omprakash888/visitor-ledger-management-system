package com.immutable.visitormanagement.controller;

import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/visitor-details")
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

    @PostMapping("/updateOutTime/{visitorId}")
    public ResponseEntity<String> updateOutTime(@PathVariable Long visitorId) {
        String response = this.visitorServices.updateOutTime(visitorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
