package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.entity.VisitorDetails;
import com.immutable.visitormanagement.repository.VisitorRepository;
import com.immutable.visitormanagement.service.VisitorService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VisitorServiceImplementation implements VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private VisitorDetails visitorDetails;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void save(VisitorDto visitorDto) {
        VisitorDetails visitorDetails = mapToVisitorDetails(visitorDto);
        visitorRepository.save(visitorDetails);
    }

    private VisitorDetails mapToVisitorDetails(VisitorDto visitorDto) {
        return this.modelMapper.map(visitorDto,VisitorDetails.class);
    }
}
