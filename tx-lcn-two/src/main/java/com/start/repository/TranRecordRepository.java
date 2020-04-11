package com.start.repository;

import com.start.entity.root.TranRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranRecordRepository extends JpaRepository<TranRecord, Integer> {


}
