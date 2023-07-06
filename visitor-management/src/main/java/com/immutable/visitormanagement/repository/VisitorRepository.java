package com.immutable.visitormanagement.repository;

import com.immutable.visitormanagement.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {

    @Query("SELECT v.visitorOrganization, COUNT(v) FROM Visitor v WHERE v.typeOfVisit = 'official' AND v.date >= :startDate " +
            "GROUP BY v.visitorOrganization")
    List<Object[]> findDataByTypeOfVisitAndStartDate(@Param("startDate") LocalDate startDate);

    @Query("SELECT COUNT(v) FROM Visitor v WHERE v.typeOfVisit = 'official' AND v.date >= :startDate")
    Integer countByTypeOfVisitAndOrganizationNameAndLocalDateGreaterThanEqual(@Param("startDate") LocalDate startDate);

    @Query("SELECT COUNT(v) FROM Visitor v WHERE v.typeOfVisit = :typeOfVisit AND v.date >= :startDate")
    long countTypeOfVisitorVisitorsFromDate(@Param("startDate") LocalDate startDate, @Param("typeOfVisit") String typeOfVisit);




}
