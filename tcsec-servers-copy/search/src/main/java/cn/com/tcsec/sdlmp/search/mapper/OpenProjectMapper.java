package cn.com.tcsec.sdlmp.search.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.tcsec.sdlmp.search.db.entity.OpenProject;
import cn.com.tcsec.sdlmp.search.export.entity.OpenProjectRanking;

@Mapper
public interface OpenProjectMapper {

	@Select("SELECT `id`, `name`, `url`, `language`, `region_id`, `issue` FROM `tb_open_project` where `name` = #{name}")
	OpenProject selectProject(@Param("name") String name) throws Exception;

	@Select("SELECT `id`, `name`, `url`, `language`, `region_id`, `issue` FROM `tb_open_project` where `id` = #{id}")
	OpenProject selectProjectByid(@Param("id") String id) throws Exception;

	@Insert("INSERT INTO `tb_open_project` (`name`, `url`, `language`, `region_id`, `issue`) VALUES (#{name}, #{url}, #{language}, #{region_id}, #{issue})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertProject(OpenProject openProject) throws Exception;

	@Update("UPDATE `tb_open_project` SET `issue`=#{issue}, `level`=#{level}, `issue_count`=#{issue_count} WHERE `id` = #{id}")
	int updateProject(@Param("issue") String issue, @Param("id") String id, @Param("level") String level,
			@Param("issue_count") int issue_count) throws Exception;

	@Select("select (issue1+issue2+issue3+issue4+issue5+issue6+issue7+issue8+issue9+issue10+issue11+issue12+issue13+issue14+" + 
			"issue15+issue16+issue17+issue18+issue19+issue20+issue21+issue22+issue23+issue24+issue25) as total,issue1,issue2," + 
			"issue3,issue4,issue5,issue6,issue7,issue8,issue9,issue10,issue11,issue12,issue13,issue14,issue15,issue16,issue17," + 
			"issue18,issue19,issue20,issue21,issue22,issue23,issue24,issue25 from (select " + 
			"sum(ifnull(json_extract(issue,'$.issue1'),0)) as issue1, sum(ifnull(json_extract(issue,'$.issue2'),0)) as issue2, " + 
			"sum(ifnull(json_extract(issue,'$.issue3'),0)) as issue3,sum(ifnull(json_extract(issue,'$.issue4'),0)) as issue4, " + 
			"sum(ifnull(json_extract(issue,'$.issue5'),0)) as issue5, sum(ifnull(json_extract(issue,'$.issue6'),0)) as issue6, " + 
			"sum(ifnull(json_extract(issue,'$.issue7'),0)) as issue7, sum(ifnull(json_extract(issue,'$.issue8'),0)) as issue8, " + 
			"sum(ifnull(json_extract(issue,'$.issue9'),0)) as issue9, sum(ifnull(json_extract(issue,'$.issue10'),0)) as issue10, " + 
			"sum(ifnull(json_extract(issue,'$.issue11'),0)) as issue11, sum(ifnull(json_extract(issue,'$.issue12'),0)) as issue12, " + 
			"sum(ifnull(json_extract(issue,'$.issue13'),0)) as issue13, sum(ifnull(json_extract(issue,'$.issue14'),0)) as issue14, " + 
			"sum(ifnull(json_extract(issue,'$.issue15'),0)) as issue15, sum(ifnull(json_extract(issue,'$.issue16'),0)) as issue16, " + 
			"sum(ifnull(json_extract(issue,'$.issue17'),0)) as issue17, sum(ifnull(json_extract(issue,'$.issue18'),0)) as issue18, " + 
			"sum(ifnull(json_extract(issue,'$.issue19'),0)) as issue19, sum(ifnull(json_extract(issue,'$.issue20'),0)) as issue20, " + 
			"sum(ifnull(json_extract(issue,'$.issue21'),0)) as issue21, sum(ifnull(json_extract(issue,'$.issue22'),0)) as issue22, " + 
			"sum(ifnull(json_extract(issue,'$.issue23'),0)) as issue23, sum(ifnull(json_extract(issue,'$.issue24'),0)) as issue24, " + 
			"sum(ifnull(json_extract(issue,'$.issue25'),0)) as issue25 from tb_open_project) t1")
	Map<String, Double> selectIssueMap() throws Exception;
	
	@Select("select name,(high+middle+low) as total, high,middle,low from (select name as name, sum(ifnull(json_extract(issue,'$.issue1'),0)"
			+ "+ifnull(json_extract(issue,'$.issue2'),0)+ifnull(json_extract(issue,'$.issue3'),0)+ifnull(json_extract(issue,'$.issue4'),0)+"
			+ "ifnull(json_extract(issue,'$.issue5'),0)+ifnull(json_extract(issue,'$.issue6'),0)+ifnull(json_extract(issue,'$.issue7'),0)+"
			+ "ifnull(json_extract(issue,'$.issue8'),0)+ifnull(json_extract(issue,'$.issue9'),0)) as high,sum(ifnull(json_extract(issue,'$.issue10'),0)"
			+ "+ifnull(json_extract(issue,'$.issue11'),0)+ifnull(json_extract(issue,'$.issue12'),0)+ifnull(json_extract(issue,'$.issue13'),0)+"
			+ "ifnull(json_extract(issue,'$.issue14'),0)+ifnull(json_extract(issue,'$.issue15'),0)+ifnull(json_extract(issue,'$.issue16'),0)+"
			+ "ifnull(json_extract(issue,'$.issue17'),0)) as middle,sum(ifnull(json_extract(issue,'$.issue18'),0)+ifnull(json_extract(issue,'$.issue19'),0)"
			+ "+ifnull(json_extract(issue,'$.issue20'),0)+ifnull(json_extract(issue,'$.issue21'),0)+ifnull(json_extract(issue,'$.issue22'),0)+"
			+ "ifnull(json_extract(issue,'$.issue23'),0)+ifnull(json_extract(issue,'$.issue24'),0)+ifnull(json_extract(issue,'$.issue25'),0)) as low " + 
			"from tb_open_project group by name) t1 order by ${sort} desc")
	List<OpenProjectRanking> selectProjectByName(@Param("sort") String sort) throws Exception;

	@Select("select name,(high+middle+low) as total, high,middle,low from (select `language` as name, sum(ifnull(json_extract(issue,'$.issue1'),0)"
			+ "+ifnull(json_extract(issue,'$.issue2'),0)+ifnull(json_extract(issue,'$.issue3'),0)+ifnull(json_extract(issue,'$.issue4'),0)+"
			+ "ifnull(json_extract(issue,'$.issue5'),0)+ifnull(json_extract(issue,'$.issue6'),0)+ifnull(json_extract(issue,'$.issue7'),0)+"
			+ "ifnull(json_extract(issue,'$.issue8'),0)+ifnull(json_extract(issue,'$.issue9'),0)) as high,sum(ifnull(json_extract(issue,'$.issue10'),0)"
			+ "+ifnull(json_extract(issue,'$.issue11'),0)+ifnull(json_extract(issue,'$.issue12'),0)+ifnull(json_extract(issue,'$.issue13'),0)+"
			+ "ifnull(json_extract(issue,'$.issue14'),0)+ifnull(json_extract(issue,'$.issue15'),0)+ifnull(json_extract(issue,'$.issue16'),0)+"
			+ "ifnull(json_extract(issue,'$.issue17'),0)) as middle,sum(ifnull(json_extract(issue,'$.issue18'),0)+ifnull(json_extract(issue,'$.issue19'),0)"
			+ "+ifnull(json_extract(issue,'$.issue20'),0)+ifnull(json_extract(issue,'$.issue21'),0)+ifnull(json_extract(issue,'$.issue22'),0)+"
			+ "ifnull(json_extract(issue,'$.issue23'),0)+ifnull(json_extract(issue,'$.issue24'),0)+ifnull(json_extract(issue,'$.issue25'),0)) as low " + 
			"from tb_open_project group by `language`) t1 order by ${sort} desc")
	List<OpenProjectRanking> selectProjectByLanguage(@Param("sort") String sort) throws Exception;
}
