package cn.com.tcsec.sdlmp.search.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.tcsec.sdlmp.search.db.entity.OpenHistory;

@Mapper
public interface OpenHistoryMapper {

	@Select("SELECT `id`, `date`, `region_id`, `issue_count` FROM `tb_open_history` where `date` = #{date} and `region_id` = #{region_id}")
	OpenHistory selectOpenHistory(@Param("date") String date, @Param("region_id") String region_id) throws Exception;

	@Insert("INSERT INTO `tb_open_history` (`date`, `region_id`, `issue_count`) VALUES (#{date}, #{region_id}, #{issue_count})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertOpenHistory(OpenHistory openHistory) throws Exception;

	@Update("UPDATE `tb_open_history` SET `issue_count` = #{issue_count} WHERE `id` = #{id}")
	int updateOpenHistory(@Param("issue_count") double issue_count, @Param("id") String id) throws Exception;

	@Select("select `date`,country,desc_cn as region,sum(issue_count) as issue_count from tb_open_history t1,tb_region t2 "
			+ "where t1.region_id = t2.id group by `date`,country,region order by `date`,issue_count desc")
	List<Map<String, String>> selectOpenHistoryForCountry() throws Exception;

	@Select("select `date`,province as region,sum(issue_count) as issue_count from tb_open_history t1,tb_region t2"
			+ " where t1.region_id = t2.id and t2.country = 'China' group by `date`,region order by `date`,issue_count desc")
	List<Map<String, String>> selectOpenHistoryForChina() throws Exception;

}