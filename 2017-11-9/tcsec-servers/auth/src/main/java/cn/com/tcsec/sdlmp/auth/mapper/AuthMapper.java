package cn.com.tcsec.sdlmp.auth.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import cn.com.tcsec.sdlmp.auth.db.entity.Plan;
import cn.com.tcsec.sdlmp.auth.db.entity.Project;
import cn.com.tcsec.sdlmp.auth.db.entity.ProjectUser;
import cn.com.tcsec.sdlmp.auth.db.entity.Task;
import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;

@Mapper
public interface AuthMapper {

	// tb_project
	@Insert("INSERT INTO tb_project (`creator_id`, `type`, `name`,`number`, `url`, `desc`, `language`,`state`) "
			+ "VALUES ( #{creator_id}, '1', #{name},#{number}, #{url}, #{desc}, #{language}, #{state})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertProject(Project project);

	@Insert("SELECT count(1) FROM tb_project where creator_id =#{user_id}")
	int selectProjectCountByUserId(String user_id);

	@Select("SELECT id, creator_id, type, name, `number`, url, `desc`, `language` , `state` FROM tb_project where id = #{id}")
	Map<String, Object> selectProjectReturnMap(@Param("id") String id);

	@Select("SELECT id, creator_id, type, name, `number`, url, `desc`, `language` , `state`, gmt_create, gmt_modified FROM tb_project "
			+ "where id = #{id}")
	Project selectProject(@Param("id") String id);

	@Select("SELECT id, creator_id, type, name, `number`, url, `desc`, `language` , `state`, gmt_create, gmt_modified FROM tb_project "
			+ "where creator_id = #{creator_id}")
	List<Map<String, Object>> selectProjectList(@Param("creator_id") String creator_id);

	@Update("    UPDATE tb_project SET name =( CASE WHEN #{name} IS NOT NULL AND #{name} != '' THEN #{name} ELSE name END ),"
			+ "			   number =( CASE WHEN #{number} IS NOT NULL AND #{number} != '' THEN #{number} ELSE number END ),"
			+ "			   url =( CASE WHEN #{url} IS NOT NULL AND #{url} != '' THEN #{url} ELSE url END ), "
			+ "			   `desc` =( CASE WHEN #{desc} IS NOT NULL AND #{desc} != '' THEN #{desc} ELSE `desc` END ),"
			+ "			   `language` =( CASE WHEN #{language} IS NOT NULL AND #{language} != '' THEN #{language} ELSE `language` END ),"
			+ "			   `state` =( CASE WHEN #{state} IS NOT NULL AND #{state} != '' THEN #{state} ELSE `state` END )  WHERE id = #{id}")
	int updateProject(Project project);

	@Delete("DELETE FROM tb_project WHERE id = #{id}")
	int deleteProject(@Param("id") String id);

	// tb_project_group
	@Select("SELECT user_id FROM tb_project_user where project_id = #{project_id} order by flag desc, gmt_create desc")
	List<String> selectAllProjectGroupUserByProjectId(@Param("project_id") String project_id) throws Exception;

	@Select("SELECT id, project_id, user_id, type, flag, gmt_create, gmt_modified FROM tb_project_user "
			+ "WHERE project_id in (select project_id from tb_project_user where user_id = #{user_id}) "
			+ "ORDER BY project_id, flag DESC")
	List<ProjectUser> selectAllProjectGroupUserByUserId(@Param("user_id") String user_id);

	@Select("select id, project_id, user_id, type, flag, gmt_create, gmt_modified from tb_project_user where user_id = #{user_id} "
			+ "order by flag desc, gmt_create desc")
	List<ProjectUser> selectAllProjectGroupByUserId(@Param("user_id") String user_id);

	@Insert("INSERT INTO tb_project_user (project_id, user_id, type, flag)"
			+ " VALUES (#{project_id}, #{user_id}, #{type}, #{flag})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertProjectGroup(ProjectUser projectUser);

	@Update("UPDATE tb_project_user SET type = #{type}, flag = #{flag} " + "WHERE id = #{id}}")
	int updateProjectGroup(ProjectUser projectUser);

	@Delete("DELETE FROM tb_project_user WHERE user_id = #{user_id} and project_id = #{project_id}")
	int deleteProjectGroup(@Param("project_id") String project_id, @Param("user_id") String user_id);

	@Delete("DELETE FROM tb_project_user WHERE  project_id = #{project_id}")
	int deleteProjectGroupByProjectId(@Param("project_id") String project_id);
	
	// tb_project_user
	@Select("SELECT user_id FROM tb_project_user where project_id = #{project_id} order by flag desc, gmt_create desc")
	List<String> selectAllProjectUserByProjectId(@Param("project_id") String project_id) throws Exception;

	@Select("SELECT id, project_id, user_id, type, flag, gmt_create, gmt_modified FROM tb_project_user "
			+ "WHERE project_id in (select project_id from tb_project_user where user_id = #{user_id}) "
			+ "ORDER BY project_id, flag DESC")
	List<ProjectUser> selectAllProjectUserByUserId(@Param("user_id") String user_id);

	@Select("select id, project_id, user_id, type, flag, gmt_create, gmt_modified from tb_project_user where user_id = #{user_id} "
			+ "order by flag desc, gmt_create desc")
	List<ProjectUser> selectAllProjectByUserId(@Param("user_id") String user_id);

	@Insert("INSERT INTO tb_project_user (project_id, user_id, type, flag)"
			+ " VALUES (#{project_id}, #{user_id}, #{type}, #{flag})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertProjectUser(ProjectUser projectUser);

	@Update("UPDATE tb_project_user SET type = #{type}, flag = #{flag} " + "WHERE id = #{id}}")
	int updateProjectUser(ProjectUser projectUser);

	@Delete("DELETE FROM tb_project_user WHERE user_id = #{user_id} and project_id = #{project_id}")
	int deleteProjectUser(@Param("project_id") String project_id, @Param("user_id") String user_id);

	@Delete("DELETE FROM tb_project_user WHERE  project_id = #{project_id}")
	int deleteProjectUserByProjectId(@Param("project_id") String project_id);

	// tb_plan
	@Insert("INSERT INTO tb_plan (`project_id`, `creator_id`, `type`,`number`, `immediately_flag`, `period_flag`, `period`, `time`, `next_time`, `state`, `desc`)"
			+ " VALUES (#{project_id}, #{creator_id}, #{type},#{number}, #{immediately_flag}, #{period_flag}, #{period}, #{time}, #{next_time}, #{state}, #{desc})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertPlan(Plan plan);

	@Select("SELECT `id`,`project_id`, `creator_id`, `type`,`number`, `immediately_flag`, `period_flag`, `period`, `time`, `next_time`, `state`, `desc`, `gmt_create`, `gmt_modified`"
			+ " FROM tb_plan where id = #{id}")
	List<Plan> selectPlan(@Param("id") String id);

	@Select("select number from tb_plan where project_id = #{project_id} order by number desc limit 0,1;")
	String selectLatestPlanNum(@Param("project_id") String project_id);

	@Select("select count(1) from tb_plan where project_id = #{project_id}")
	int selectPlanCountByProjectId(@Param("project_id") String project_id);

	@Select("select project_id, project_creator_id, project_type, project_name, project_number, project_url, project_desc, project_language, plan_id, plan_number,  "
			+ "immediately_flag, period_flag, period, plan_time, next_time, plan_state, plan_desc,scan_count from  "
			+ "(select t1.`id` as project_id, t1.`creator_id` as project_creator_id, t1.`type` as project_type, t1.`name` as project_name, t1.`number` as project_number, "
			+ "t1.`url` as project_url, t1.`desc` as project_desc, t1.`language` as project_language, t2.`id` as plan_id, plan_number, t2.`immediately_flag`, t2.`period_flag`, "
			+ "t2.`period`, t2.`time` as plan_time, t2.`next_time`, t2.`state` as plan_state, t2.`desc` as plan_desc from (select `id` , `creator_id`, `type`, `name`, "
			+ "`number`, `url`, `desc`, `language`, `state` from tb_project where id in (SELECT project_id FROM tb_project_user where user_id = #{user_id} order by flag desc)) t1, "
			+ "(select `id`, `project_id`, `number` as plan_number , `immediately_flag`, `period_flag`, `period`, `time`, `next_time`, `state`, `desc` from tb_plan ) t2  "
			+ "where t2.project_id = t1.id) t3 LEFT JOIN (select plan_id as task_plan_id,count(1) as scan_count from tb_task group by plan_id ) t4  "
			+ "on t4.task_plan_id = t3.plan_id order by plan_state,plan_number ")
	List<Map<String, Object>> selectPlanList(@Param("user_id") String user_id);

	@Select("SELECT `id`, `project_id`, `creator_id`, `type`, `number`,`immediately_flag`, `period_flag`, `period`, `time`, `next_time`, `state`, `desc`, `gmt_create`, `gmt_modified` "
			+ "FROM `tb_plan` where (`state` = 1 and (next_time <= now() or next_time is null)) or immediately_flag = '1'")
	List<Plan> selectScanPlan();

	@Update("UPDATE `tb_plan` SET `period_flag` =( CASE WHEN #{period_flag} IS NOT NULL AND #{period_flag} != '' THEN #{period_flag} ELSE `period_flag` END ), "
			+ "`immediately_flag` =( CASE WHEN #{immediately_flag} IS NOT NULL AND #{immediately_flag} != '' THEN #{immediately_flag} ELSE `immediately_flag` END ), "
			+ "`period` =( CASE WHEN #{period} IS NOT NULL AND #{period} != '' THEN #{period} ELSE `period` END ), "
			+ "`time` =( CASE WHEN #{time} IS NOT NULL AND #{time} != '' THEN #{time} ELSE `time` END ), "
			+ "`next_time` = #{next_time}, "
			+ "`state` =( CASE WHEN #{state} IS NOT NULL AND #{state} != '' THEN #{state} ELSE `state` END ), "
			+ "`desc` =( CASE WHEN #{desc} IS NOT NULL AND #{desc} != '' THEN #{desc} ELSE `desc` END ) WHERE `id` = #{id}")
	int updatePlan(Plan plan);

	@Delete("DELETE FROM tb_plan WHERE id = #{id}")
	int deletePlan(@Param("id") String id);

	// tb_task
	@Insert("INSERT INTO tb_task ( plan_id, project_id, creator_id, `state`, star_time) "
			+ "VALUES (#{plan_id}, #{project_id}, #{creator_id}, '0', now())")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertTask(Task task);

	@Select("SELECT id, plan_id, project_id, creator_id, `state`,issue_count,file_count,"
			+ "extend_issue, resolution_rate, info, star_time, finish_time, gmt_create, gmt_modified FROM tb_task where id = #{id}")
	Task selectTask(@Param("id") String id);

	@Select("SELECT `id`, `project_id`, `creator_id`, `plan_id`, `state`, `star_time`, `finish_time`, `issue_count`,"
			+ "			 `file_count`, `extend_issue`, `info`, `gmt_create`, `gmt_modified` FROM tb_task "
			+ "			where id = (select id from tb_task where plan_id in (select id from tb_plan where project_id = #{project_id}) order by id desc limit 0,1)")
	Task selectLastTaskByProjectId(@Param("project_id") String project_id);

	@Select("SELECT id, plan_id, project_id, creator_id, `state`,issue_count,file_count,"
			+ "extend_issue, resolution_rate,star_time, finish_time "
			+ "FROM tb_task where plan_id = #{plan_id} order by id desc")
	List<Map<String, Object>> selectTaskByPlanId(@Param("plan_id") String plan_id);

	@Select("select substring_index((@i:=@i+1),'.',1) as scan_index,t1.issue_count,extend_issue as resolution_count,ifnull(t1.resolution_rate,'0.00') as "
			+ "resolution_rate,t1.id as task_id from tb_task t1,(select @i:=0) t2 where plan_id in (select id from tb_plan where project_id =#{project_id}) and `state`='2' order by id")
	List<Map<String, Object>> selectTaskIndexByProjectId(@Param("project_id") String project_id);

	@Select("select substring_index((@i:=@i+1),'.',1) as scan_index,grade from tb_task t1,(select @i:=0) t2 where plan_id in "
			+ "(select id from tb_plan where project_id =#{project_id}) and `state`='2' order by id")
	List<Map<String, Object>> selectTaskGradeByProjectId(@Param("project_id") String project_id);

	@Update("UPDATE tb_task SET  `state` = #{state}, finish_time = #{finish_time}, issue_count = #{issue_count},"
			+ " file_count = #{file_count}, extend_issue = #{extend_issue},`info` = #{info} WHERE id = #{id}")
	int updateTask(Task task);

	@Delete("DELETE FROM tb_task WHERE id = #{id}")
	int deleteTask(@Param("id") String id);

	// tb_report
	@Insert("INSERT INTO tb_report ( task_id, `info`, `issue_key`) VALUES (#{task_id}, #{info}, #{issue_key})")
	int insertReport(@Param(value = "task_id") String task_id, @Param(value = "info") String info,
			@Param(value = "issue_key") String issue_key) throws Exception;

	@Select(" SELECT count(1) as total FROM tb_report where task_id = #{task_id}")
	int selectReportTotal(@Param("task_id") String task_id);

	@Select("SELECT t1.task_id,t3.name as project_name,t3.number as project_number,t4.number as plan_number,t3.`language` as project_language,json_extract(t1.info,'$.type') as type, "
			+ "json_extract(t1.info,'$.check_name') as check_name,json_extract(t1.info,'$.description') as description,json_extract(t1.info,'$.categories[0]') as categories, "
			+ "json_extract(t1.info,'$.location.path') as location,json_extract(t1.info,'$.mainCode') as mainCode,json_extract(t1.info,'$.mainCodeBeginLine') as mainCodeBeginLine, "
			+ "case when json_extract(t1.info,'$.location.positions.begin.line') is null then json_extract(t1.info,'$.location.lines.begin') else json_extract(t1.info,'$.location.positions.begin.line') end as begin_line, "
			+ "case when json_extract(t1.info,'$.location.positions.end.line') is null then json_extract(t1.info,'$.location.lines.end') else json_extract(t1.info,'$.location.positions.end.line') end as end_line, "
			+ "json_extract(t1.info,'$.location.positions.begin.column') as begin_column, json_extract(t1.info,'$.location.positions.end.column') as end_column, json_extract(t1.info,'$.content.body') as body, "
			+ "json_extract(t1.info,'$.severity') as seve,json_extract(t1.info,'$.engine_name') as engine_name,json_extract(t1.info,'$.message') as message FROM tb_report t1,tb_task t2,tb_project t3,tb_plan t4 "
			+ "where t1.task_id = #{task_id} and t2.id = t1.task_id and t3.id = t2.project_id and t4.id = t2.plan_id order by location, begin_line, end_line  limit #{current}, #{pageSize}")
	List<Map<String, Object>> selectReport(@Param("task_id") String task_id, @Param("current") int current,
			@Param("pageSize") int pageSize, @Param("sort") String sort);

	@Select("select t1.id as task_id,t3.number as plan_number,t1.creator_id,t1.`state`,t1.star_time,t1.finish_time,t1.issue_count,t1.file_count, "
			+ "t1.extend_issue,t1.resolution_rate,t2.type as project_type,t2.name as project_name,t2.number as project_number,t2.url as project_url, "
			+ "t2.`language` as project_language from tb_task t1,tb_project t2,tb_plan t3 where t1.project_id = t2.id and t1.plan_id = t3.id and t1.creator_id = #{user_id} order by t1.id desc")
	List<Map<String, Object>> selectReportList(@Param("user_id") String user_id);

	@Delete("DELETE FROM tb_report WHERE id = #{id}")
	int deleteReportById(@Param("id") String id);

	// report_analyze
	@Select("call report_analyze(#{project_id})")
	@Options(statementType = StatementType.CALLABLE)
	Map<String, Object> callOutline(@Param("project_id") String project_id);

	@Select("SELECT `id`, `project_id`, `creator_id`, `plan_id`, `state`, TIMESTAMPDIFF(SECOND,star_time, finish_time) as scan_time, "
			+ "`issue_count`, `file_count`, `extend_issue`, `resolution_rate`, `grade`, `info` FROM `tb_task` where `state` = '2'"
			+ " and plan_id in(select id from tb_plan where project_id = #{project_id}) order by id desc")
	List<Map<String, Object>> selectTaskList(@Param("project_id") String project_id);

	@Select("select b.id as project_id, b.creator_id as creator_id, b.type as type, b.name as project_name, "
			+ "b.url as project_url, b.`language` as project_language, a.id as task_id from tb_task a, "
			+ "tb_project b where a.project_id = b.id and a.`state` = 0 order by a.id")
	@Options(useCache = false, timeout = 500)
	List<ScheduledTask> scheduledScan() throws Exception;

	@Select("SELECT issue_key FROM tb_report where task_id = (select max(id) from tb_task where project_id = #{project_id} and `state` = '2')")
	List<String> selectReportIssueKeyList(String project_id) throws Exception;

	@Select("select issue_count from tb_task where project_id = #{project_id} and `state` = '2' order by id desc limit 0,1")
	String selectLastIssueCount(String project_id) throws Exception;

	@Update("UPDATE tb_task SET `state` = '1', star_time = now() WHERE id = #{id}")
	@Options(timeout = 500)
	int updateTaskWhenStart(String id) throws Exception;

	@Update("UPDATE tb_task SET `state` = #{state}, finish_time = now(), issue_count = #{issue_count},"
			+ " file_count = #{file_count}, extend_issue = #{extend_issue}, `resolution_rate` = #{resolution_rate},"
			+ " `info`=#{info},`grade`=#{grade} WHERE id = #{id}")
	int updateTaskWhenFinsh(@Param(value = "id") String id, @Param(value = "state") String state,
			@Param(value = "issue_count") int issue_count, @Param(value = "file_count") int file_count,
			@Param(value = "extend_issue") int extend_issue, @Param(value = "resolution_rate") String resolution_rate,
			@Param(value = "info") String info, @Param(value = "grade") String grade) throws Exception;

	// auth
	// project_id read
	@Select("select id from tb_project_user where user_id = #{user_id} and project_id = #{project_id} limit 0,1")
	String selectAuthProjectIdRead(@Param(value = "user_id") String user_id,
			@Param(value = "project_id") String project_id) throws Exception;

	// project _id write
	// project_user write
	@Select("select id from tb_project_user where user_id = #{user_id} and project_id = #{project_id} and flag = '1' limit 0,1")
	String selectAuthProjectAndUserWrite(@Param(value = "user_id") String user_id,
			@Param(value = "project_id") String project_id) throws Exception;

	// plan_id write
	@Select("select t1.id from (select * from tb_plan where id = #{plan_id}) t1,(select * from tb_project_user where user_id=#{user_id} and flag='1') t2 where t1.project_id = t2.project_id limit 0,1")
	String selectAuthPlanIdWrite(@Param(value = "user_id") String user_id, @Param(value = "plan_id") String plan_id)
			throws Exception;

	// plan_id read
	@Select("select t1.id from (select * from tb_plan where id = #{plan_id}) t1,(select * from tb_project_user where user_id=#{user_id}) t2 where t1.project_id = t2.project_id limit 0,1")
	String selectAuthPlanIdRead(@Param(value = "user_id") String user_id, @Param(value = "plan_id") String plan_id)
			throws Exception;

	// task_id read
	@Select("select t1.id from (select * from tb_task where id = #{task_id}) t1,(select * from tb_project_user where user_id=#{user_id}) t2 where t1.project_id = t2.project_id limit 0,1")
	String selectAuthTaskIdRead(@Param(value = "user_id") String user_id, @Param(value = "task_id") String task_id)
			throws Exception;

	// report file
	@Select("select t1.creator_id,t1.star_time,t1.finish_time,t1.issue_count,t1.file_count,t1.extend_issue,t1.resolution_rate,t1.grade,t2.name as project_name"
			+ ",t2.number as project_number,t2.url as project_url,t2.`desc` as project_desc,t2.`language` as project_language,t3.number as plan_number"
			+ " from tb_task t1,tb_project t2,tb_plan t3 where t1.id = #{task_id} and t2.id = t1.project_id and t3.id = t1.plan_id")
	Map<String, String> selectReportFileHeader(@Param(value = "task_id") String task_id) throws Exception;

	@Select("SELECT json_extract(t1.info,'$.type') as type,json_extract(t1.info,'$.check_name') as check_name,json_extract(t1.info,'$.description') as description,json_extract(t1.info,'$.categories[0]') as categories, "
			+ "json_extract(t1.info,'$.location.path') as location,json_extract(t1.info,'$.mainCode') as mainCode,json_extract(t1.info,'$.mainCodeBeginLine') as mainCodeBeginLine,  "
			+ "case when json_extract(t1.info,'$.location.positions.begin.line') is null then json_extract(t1.info,'$.location.lines.begin') else json_extract(t1.info,'$.location.positions.begin.line') end as begin_line, "
			+ "case when json_extract(t1.info,'$.location.positions.end.line') is null then json_extract(t1.info,'$.location.lines.end') else json_extract(t1.info,'$.location.positions.end.line') end as end_line, "
			+ "json_extract(t1.info,'$.location.positions.begin.column') as begin_column, json_extract(t1.info,'$.location.positions.end.column') as end_column, json_extract(t1.info,'$.content.body') as body, "
			+ "json_extract(t1.info,'$.severity') as seve,json_extract(t1.info,'$.engine_name') as engine_name,json_extract(t1.info,'$.message') as message FROM tb_report t1 where t1.task_id = #{task_id} order by location, begin_line, end_line ")
	List<Map<String, String>> selectReportFileBody(@Param(value = "task_id") String task_id) throws Exception;

}
