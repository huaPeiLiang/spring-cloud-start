package com.start.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.start.entity.root.TranRecord;
import com.start.repository.TranRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranRecordServiceImpl {

    @Autowired
    private TranRecordRepository tranRecordRepository;

    @LcnTransaction
    @Transactional
    public TranRecord add(Integer accountId, Double changeAmount, String changeType){
        TranRecord tranRecord = new TranRecord();
        tranRecord.setAccountId(accountId);
        tranRecord.setChangeAmount(changeAmount);
        tranRecord.setChangeType(changeType);
        return tranRecordRepository.save(tranRecord);
    }

}
