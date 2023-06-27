package com.immutable.visitormanagement.repository;

import com.immutable.visitormanagement.entity.VisitorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorDetails,Long> {

}
