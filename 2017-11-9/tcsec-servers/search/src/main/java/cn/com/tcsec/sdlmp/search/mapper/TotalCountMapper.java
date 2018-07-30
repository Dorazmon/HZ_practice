package cn.com.tcsec.sdlmp.search.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.tcsec.sdlmp.search.db.entity.OpenTotal;

@Mapper
public interface TotalCountMapper {

	@Select("SELECT `file_count`, `line_count`, `high_issue_count`, `mid_issue_count`, `low_issue_count` FROM `tb_open_total` where id = '1' ")
	OpenTotal selectOpenTotal() throws Exception;

	@Insert("INSERT INTO `tb_open_total` (`id`, `file_count`, `high_issue_count`, `mid_issue_count`, `low_issue_count`) "
			+ "VALUES ('1',#{file_count}, #{issue_count}, #{high_issue_count}, #{mid_issue_count}, #{low_issue_count})")
	int insertOpenTotal(OpenTotal openTotal) throws Exception;

	@Update("UPDATE `tb_open_total` SET `file_count` = #{file_count}, `high_issue_count`=#{high_issue_count}, "
			+ "`mid_issue_count`=#{mid_issue_count}, `low_issue_count`=#{low_issue_count}, "
			+ " `line_count` = #{line_count} WHERE `id` = '1'")
	int updateOpenTotal(OpenTotal openTotal) throws Exception;

}