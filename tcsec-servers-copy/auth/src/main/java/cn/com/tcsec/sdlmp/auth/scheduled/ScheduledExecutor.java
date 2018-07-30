package cn.com.tcsec.sdlmp.auth.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.tcsec.sdlmp.auth.db.entity.Plan;
import cn.com.tcsec.sdlmp.auth.db.entity.Project;
import cn.com.tcsec.sdlmp.auth.db.entity.Task;
import cn.com.tcsec.sdlmp.auth.service.AuthService;
import cn.com.tcsec.sdlmp.common.entity.ScheduledTask;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;

public class ScheduledExecutor implements Runnable {
	private final static Logger logger = LoggerFactory.getLogger(ScheduledExecutor.class);

	private AuthService authService;
	private Plan plan;

	public ScheduledExecutor(AuthService authService, Plan plan) {
		this.authService = authService;
		this.plan = plan;
	}

	@Override
	public void run() {
		Task task = new Task();
		task.setCreator_id(plan.getCreator_id());
		task.setPlan_id(plan.getId());
		task.setProject_id(plan.getProject_id());

		if (!ErrorContent.OK.equals(authService.addTask(task))) {
			logger.error("add task 出错 ！  ");
			return;
		}

		Project project = authService.scheduled_getProject(plan.getProject_id());
		if (project == null) {
			logger.error("找不到对应的 project 信息！ ");
			return;
		}

		ScheduledTask scheduledTask = new ScheduledTask();

		scheduledTask.setCreator_id(project.getCreator_id());
		scheduledTask.setProject_id(project.getId());
		scheduledTask.setProject_language(project.getLanguage());
		scheduledTask.setProject_name(project.getName());
		scheduledTask.setProject_url(project.getUrl());
		scheduledTask.setTask_id(task.getId());
		scheduledTask.setType(project.getType());
		scheduledTask.setReturnMsqDestName("auth.docker.addReport");

		authService.scheduled_sendToDocker(scheduledTask);
	}
}
