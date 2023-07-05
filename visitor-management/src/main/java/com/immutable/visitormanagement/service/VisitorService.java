package com.immutable.visitormanagement.service;


import com.immutable.visitormanagement.dto.VisitorDto;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface VisitorService {

    VisitorDto save(VisitorDto visitorDto);

    List<VisitorDto> getAllVisitors();


    VisitorDto getVisitorById(Long visitorId);

    String updateOutTime(Long visitorId);
}
