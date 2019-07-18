package com.jellyfish.mapper;

import org.apache.ibatis.annotations.Select;

public interface SequenceMapper {
	/*菜单序列*/
	@Select("select SEQ_ETSUSER.nextval as value from dual")
	String selectEtUserSeq();

}
