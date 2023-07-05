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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    private final VisitorUtilities visitorUtilities;

    private final ModelMapper modelMapper;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, VisitorUtilities visitorUtilities, ModelMapper modelMapper) {
        this.visitorRepository = visitorRepository;
        this.visitorUtilities = visitorUtilities;
        this.modelMapper = modelMapper;
    }

    @Override
    public VisitorDto save(VisitorDto visitorDto) {
        Visitor visitor = mapToVisitor(visitorDto);
        visitor.setOutTime(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));
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

    @Override
    public String updateOutTime(Long visitorId) {
        Visitor visitor = this.visitorRepository.findById(visitorId).orElseThrow(() -> new ResourceNotFoundException("visitor","id",visitorId));
        LocalDateTime localDateTime = visitor.getInTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if(LocalDateTime.now().isAfter(localDateTime.plusHours(24))) {
            return "your visitor entry is expired, please create new visitor entry";
        }
        visitor.setOutTime(new Date());
        this.visitorRepository.save(visitor);
        return "Thank you for your time";
    }


    private Visitor mapToVisitor(VisitorDto visitorDto) {
        return this.modelMapper.map(visitorDto, Visitor.class);
    }

    private VisitorDto mapToVisitorDto(Visitor visitor) {
        return this.modelMapper.map(visitor,VisitorDto.class);
    }
}
