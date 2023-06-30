package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.entity.Visitor;
import com.immutable.visitormanagement.repository.VisitorRepository;
import com.immutable.visitormanagement.service.VisitorService;
import com.immutable.visitormanagement.exception.ResourceNotFoundException;
import com.immutable.visitormanagement.utility.VisitorUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {


    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitorUtilities visitorUtilities;


    @Autowired
    private ModelMapper modelMapper;
    @Override
    public VisitorDto save(VisitorDto visitorDto) {
        Visitor visitor = mapToVisitor(visitorDto);
        Visitor addVisitor = visitorRepository.save(visitor);
        visitorUtilities.sendEmail(mapToVisitorDto(addVisitor));
        return mapToVisitorDto(addVisitor);
    }

    @Override
    public List<VisitorDto> getAllVisitors() {
        List<Visitor> visitors =  this.visitorRepository.findAll();
        List<VisitorDto> visitorDto = visitors.stream().map(this::mapToVisitorDto).toList();
        return visitorDto;
    }

    @Override
    public VisitorDto getVisitorById(Long visitorId) {
        Visitor visitor = this.visitorRepository.findById(visitorId).orElseThrow(() -> new ResourceNotFoundException("Visitor","Id",visitorId));
        return mapToVisitorDto(visitor);
    }


    private Visitor mapToVisitor(VisitorDto visitorDto) {
        return this.modelMapper.map(visitorDto, Visitor.class);
    }

    private VisitorDto mapToVisitorDto(Visitor visitor) {
        return this.modelMapper.map(visitor,VisitorDto.class);
    }
}
