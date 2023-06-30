package com.immutable.visitormanagement.repository;

import com.immutable.visitormanagement.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {

}
