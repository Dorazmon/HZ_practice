package cn.com.tcsec.sdlmp.notify.mapper;

import cn.com.tcsec.sdlmp.notify.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotifyMapper {
	@Select("SELECT id, type, user_id, contact, subject, message, approve, gmt_create, gmt_modified FROM tb_notify where contact = #{contact} order by id desc limit 1")
	Message selectMessageByContact(@Param("contact") String contact) throws Exception;

	@Insert("INSERT INTO tb_notify (type, user_id,contact, subject, message)"
			+ " values(#{type},#{user_id}, #{contact}, #{subject}, #{message})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertMessage(Message msg) throws Exception;
}
