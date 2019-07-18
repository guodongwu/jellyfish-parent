package com.jellyfish.service.impl;

import com.jellyfish.annotation.DynamicSource;
import com.jellyfish.mapper.SequenceMapper;
import com.jellyfish.service.ISequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceServiceImpl implements ISequenceService {
	@Autowired
	private SequenceMapper sequenceMapper;
	@DynamicSource(value = "oracleDataSource")
	@Override
	public String selectEtUserSeq() {
		return sequenceMapper.selectEtUserSeq();
	}
}
