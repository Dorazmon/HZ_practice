package cn.com.tcsec.sdlmp.search.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.tcsec.sdlmp.search.db.entity.Region;

@Mapper
public interface RegionMapper {

	@Select("select id,country,province from tb_region order by id limit #{startLine},1")
	Region selectRegion(@Param("startLine") int startLine) throws Exception;

	@Select("SELECT count(1) FROM tb_region")
	int selectCount() throws Exception;

	@Insert("INSERT INTO tb_company (name, url, `desc`)  VALUES (#{name}, #{url}, #{desc})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertRegion(Region region) throws Exception;

	@Update("UPDATE tb_company SET name = #{name}, url = #{url}, `desc` = #{desc}, `state` = #{state} WHERE id = #{id}")
	int updateRegion(Region region) throws Exception;

	@Delete("DELETE FROM tb_project WHERE id = #{id}")
	int deleteRegion(@Param("id") String id) throws Exception;

}