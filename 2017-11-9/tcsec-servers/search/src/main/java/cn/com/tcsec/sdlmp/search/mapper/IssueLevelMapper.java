package cn.com.tcsec.sdlmp.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.com.tcsec.sdlmp.common.entity.IssueLevel;

@Mapper
public interface IssueLevelMapper {

	@Select("select issue,desc_english,desc_chinese,`level` from tb_issue_level where issue=#{issue}")
	IssueLevel selectIssueLevel(String issue) throws Exception;

	@Select("select issue,desc_english,desc_chinese,`level` from tb_issue_level order by id")
	List<IssueLevel> selectIssueLevelList() throws Exception;
}
