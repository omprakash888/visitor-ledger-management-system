package com.immutable.visitormanagement.service;


import com.immutable.visitormanagement.dto.VisitorDto;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface VisitorService {

    public VisitorDto save(VisitorDto visitorDto);

    public List<VisitorDto> getAllVisitors();


    public VisitorDto getVisitorById(Long visitorId);
}
