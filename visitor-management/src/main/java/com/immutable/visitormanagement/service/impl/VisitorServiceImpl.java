package com.immutable.visitormanagement.service.impl;

import com.immutable.visitormanagement.dto.PersonalAndOfficialByOrganization;
import com.immutable.visitormanagement.dto.VisitorDto;
import com.immutable.visitormanagement.entity.Employee;
import com.immutable.visitormanagement.entity.Visitor;
import com.immutable.visitormanagement.repository.EmployeeRepository;
import com.immutable.visitormanagement.repository.VisitorRepository;
import com.immutable.visitormanagement.request.DashboardRequest;
import com.immutable.visitormanagement.service.VisitorService;
import com.immutable.visitormanagement.exception.ResourceNotFoundException;
import com.immutable.visitormanagement.utility.VisitorUtilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorUtilities visitorUtilities;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, VisitorUtilities visitorUtilities, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.visitorRepository = visitorRepository;
        this.visitorUtilities = visitorUtilities;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public VisitorDto save(VisitorDto visitorDto) {
        Visitor visitor = mapToVisitor(visitorDto);
        visitor.setDate(LocalDate.now());
        visitor.setInTime(LocalTime.now());
        visitor.setOutTime(LocalTime.now().plusHours(24));
        Visitor addVisitor = visitorRepository.save(visitor);
        Employee employee = this.employeeRepository.findByEmployeeName(visitorDto.getWhomToMeet());
        if(employee != null) {
            List<Visitor> oldVisitor = employee.getVisitor();
            oldVisitor.add(visitor);
            employee.setVisitor(oldVisitor);
            this.employeeRepository.save(employee);
        }
        visitorUtilities.sendEmail(mapToVisitorDto(addVisitor));
        return mapToVisitorDto(addVisitor);
    }

    @Override
    public List<VisitorDto> getAllVisitors() {
        List<Visitor> visitors =  this.visitorRepository.findAll();
        return visitors.stream().map(this::mapToVisitorDto).toList();
    }

    @Override
    public VisitorDto getVisitorById(Long visitorId) {
        Visitor visitor = this.visitorRepository.findById(visitorId).orElseThrow(() -> new ResourceNotFoundException("Visitor","Id",visitorId));
        return mapToVisitorDto(visitor);
    }

    @Override
    public String updateOutTime(Long visitorId) {
        Visitor visitor = this.visitorRepository.findById(visitorId).orElseThrow(() -> new ResourceNotFoundException("visitor","id",visitorId));
        LocalDateTime localDateTime = LocalDateTime.of(visitor.getDate(), visitor.getInTime());

        if(LocalDateTime.now().isAfter(localDateTime.plusHours(24))) {
            return "your visitor entry is expired, please create new visitor entry";
        }
        visitor.setOutTime(LocalTime.now());
        this.visitorRepository.save(visitor);
        return "Thank you for your time";
    }

    @Override
    public Map<String, Double> getPieChartData(DashboardRequest dashboardRequest) {
        LocalDate localDate = LocalDate.parse(dashboardRequest.getDate());
        List<Object[]> pieChartData = this.visitorRepository.findDataByTypeOfVisitAndStartDate(localDate,"official");
        int totalCount = this.visitorRepository.countByTypeOfVisitAndOrganizationNameAndLocalDateGreaterThanEqual(localDate);
        return listToMap(pieChartData,totalCount);
    }

    @Override
    public Map<String, Long> getBarGraphData(DashboardRequest dashboardRequest) {
        LocalDate localDate = LocalDate.parse(dashboardRequest.getDate());        Long officialVisitCount = this.visitorRepository.countTypeOfVisitorVisitorsFromDate(localDate,"official");
        Long personalVisitCount = this.visitorRepository.countTypeOfVisitorVisitorsFromDate(localDate,"personal");
        Map<String,Long> visitorCount = new HashMap<>();
        visitorCount.put("personal",personalVisitCount);
        visitorCount.put("official",officialVisitCount);
        return visitorCount;
    }

    @Override
    public List<PersonalAndOfficialByOrganization> getPersonalAndOfficialByOrganization(DashboardRequest dashboardRequest) {
        LocalDate localDate = LocalDate.parse(dashboardRequest.getDate());        List<Object[]> officialData = this.visitorRepository.findDataByTypeOfVisitAndStartDate(localDate,"official");
        List<Object[]> personalData = this.visitorRepository.findDataByTypeOfVisitAndStartDate(localDate,"personal");

        Map<String, Long> personalMap = personalData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Long) row[1]));

        return officialData.stream()
                .map(row -> {
                    String organizationName = (String) row[0];
                    Long officialCount = (Long) row[1];
                    Long personalCount = personalMap.getOrDefault(organizationName, 0L);
                    return new PersonalAndOfficialByOrganization(organizationName, personalCount, officialCount);
                })
                .toList();
    }

    @Override
    public long getActiveVisitors() {
        return this.visitorRepository.countActiveVisitorsByDateAndTime(LocalDate.now(),LocalTime.now());
    }

    @Override
    public Double getBusiestCheckInTime() {
        Double time = (this.visitorRepository.findAverageInTime()/3600);
        return Double.valueOf(decfor.format(time));
    }

    @Override
    public List<String> getAllVisitorOrganization() {
        return this.visitorRepository.findAllOrganization();
    }

    private Visitor mapToVisitor(VisitorDto visitorDto) {
        return this.modelMapper.map(visitorDto, Visitor.class);
    }

    private VisitorDto mapToVisitorDto(Visitor visitor) {
        return this.modelMapper.map(visitor,VisitorDto.class);
    }

    private Map<String, Double> listToMap(List<Object[]> list, int totalCount) {
        Map<String, Double> map = new HashMap<>(list.size());
        for (Object[] arr : list) {
            String stringValue = (String) arr[0];
            Double intValue = (Double.parseDouble(arr[1].toString()) * 100.0) / totalCount;
            map.put(stringValue, Double.valueOf(decfor.format(intValue)));
        }
        return map;
    }

}
