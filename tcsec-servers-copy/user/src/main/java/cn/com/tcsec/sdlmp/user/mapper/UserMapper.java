package cn.com.tcsec.sdlmp.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.com.tcsec.sdlmp.user.entity.Company;
import cn.com.tcsec.sdlmp.user.entity.User;

@Mapper
public interface UserMapper {

	@Select("SELECT id, user_id, phone, email, passwd, name, company_id, role, state, gmt_create, gmt_modified FROM tb_user where user_id = #{userId}")
	User selectUserByUserId(@Param("userId") String userId) throws Exception;

	@Select("SELECT user_id, phone, email, name, company_id, role, state FROM tb_user where user_id = #{userId}")
	Map<String, String> selectUser(@Param("userId") String userId) throws Exception;

	@Select("SELECT id, user_id, phone, email, passwd, name, company_id, role, state, gmt_create, gmt_modified FROM tb_user where phone = #{phone}")
	User selectUserByPhone(@Param("phone") String phone) throws Exception;

	@Select("SELECT id, user_id, phone, email, passwd, name, company_id, role, state, gmt_create, gmt_modified FROM tb_user where email = #{email}")
	User selectUserByEmail(@Param("email") String email) throws Exception;

	@Select("SELECT user_id, name FROM tb_user where company_id = #{company_id}")
	List<User> selectUserByCompany(@Param("company_id") String company_id) throws Exception;

	@Insert("INSERT INTO tb_user (user_id, phone, email,name, passwd,role, state)"
			+ " values(#{user_id}, #{phone}, #{email},#{name}, #{passwd},#{role},#{state})")
	void insertUser(User user) throws Exception;

	@Update("UPDATE tb_user SET phone =( CASE WHEN #{phone} IS NOT NULL AND #{phone} != '' THEN #{phone} ELSE phone END ), "
			+ "    email =( CASE WHEN #{email} IS NOT NULL AND #{email} != '' THEN #{email} ELSE email END ), "
			+ "    passwd =( CASE WHEN #{passwd} IS NOT NULL AND #{passwd} != '' THEN #{passwd} ELSE passwd END ),  "
			+ "    name =( CASE WHEN #{name} IS NOT NULL AND #{name} != '' THEN #{name} ELSE `name` END ), "
			+ "    company_id =( CASE WHEN #{company_id} IS NOT NULL AND #{company_id} != '' THEN #{company_id} ELSE `company_id` END ),  "
			+ "    `role` =( CASE WHEN #{role} IS NOT NULL AND #{role} != '' THEN #{role} ELSE `role` END ), "
			+ "    `state` =( CASE WHEN #{state} IS NOT NULL AND #{state} != '' THEN #{state} ELSE `state` END ) WHERE user_id = #{user_id}")
	int updateUserByUserId(User user) throws Exception;

	@Update("UPDATE tb_user SET passwd=#{passwd} WHERE phone = #{phone}")
	int updateUserPasswdByPhone(@Param("phone") String phone, @Param("passwd") String passwd) throws Exception;

	// tb_company
	@Select("SELECT id, name, url, `desc`, `state`, gmt_create, gmt_modified FROM tb_company where name = #{name}")
	Company selectCompany(@Param("name") String name) throws Exception;

	@Insert("INSERT INTO tb_company (name, url, `desc`)  VALUES (#{name}, #{url}, #{desc})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertCompany(Company company) throws Exception;

	@Update("UPDATE tb_company SET name = #{name}, url = #{url}, `desc` = #{desc}, `state` = #{state} WHERE id = #{id}")
	int updateCompany(Company company) throws Exception;

	@Delete("DELETE FROM tb_project WHERE id = #{id}")
	int deleteCompany(@Param("id") String id) throws Exception;

	// tb_auth
	@Select("select name,menu_id,sort,url,parent,icon,auth from tb_auth where auth >= #{auth} order by parent,sort desc")
	List<Map<String, Object>> selectAuthList(@Param("auth") String auth) throws Exception;
}
