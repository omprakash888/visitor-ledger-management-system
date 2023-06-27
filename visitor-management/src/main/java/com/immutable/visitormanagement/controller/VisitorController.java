package com.immutable.visitormanagement.controller;

import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/visitor-details")
public class VisitorController {

    @Autowired
    private VisitorService visitorServices;

    @PostMapping("/create")
    public ResponseEntity<VisitorDto> createVisitorEntry(@Valid @RequestBody VisitorDto visitorDto) {
        visitorServices.save(visitorDto);
        return null;
    }
}
