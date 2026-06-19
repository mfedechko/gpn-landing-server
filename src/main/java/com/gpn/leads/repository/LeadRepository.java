package com.gpn.leads.repository;

import com.gpn.leads.model.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<LeadEntity, Long> {

    List<LeadEntity> findAllByOrderByCreatedAtDesc();

}
