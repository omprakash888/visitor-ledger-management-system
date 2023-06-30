package com.immutable.visitormanagement.service;


import com.immutable.visitormanagement.dto.VisitorDto;

import java.util.List;


public interface VisitorService {

    public void save(VisitorDto visitorDto);

    public List<VisitorDto> getAllVisitors();


    public VisitorDto getVisitorById(Long visitorId);
}
