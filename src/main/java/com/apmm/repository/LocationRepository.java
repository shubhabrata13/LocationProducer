package com.apmm.repository;

import com.apmm.domain.Location;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LocationRepository extends JpaRepository<Location, String>{

}
