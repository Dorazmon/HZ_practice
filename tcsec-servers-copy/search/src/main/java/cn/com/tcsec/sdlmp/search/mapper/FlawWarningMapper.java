package cn.com.tcsec.sdlmp.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.com.tcsec.sdlmp.search.db.entity.WarnCount;

@Mapper
public interface FlawWarningMapper {

	@Insert("INSERT INTO `tb_warn` (`warn`, `url`, `date`) VALUES (#{value},#{url},#{date})")
	int insertCountList(WarnCount warnCount) throws Exception;

	@Select("select `warn` as value,`url`,`date` from tb_warn order by id desc")
	List<WarnCount> selectWarnCountList() throws Exception;
	
	@Select("select count(*) from tb_warn order by id desc")
	int selectTotalCount() throws Exception;
}
