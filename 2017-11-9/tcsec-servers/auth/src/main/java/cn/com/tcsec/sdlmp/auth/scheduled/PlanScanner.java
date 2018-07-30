package cn.com.tcsec.sdlmp.auth.scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.tcsec.sdlmp.auth.db.entity.Plan;
import cn.com.tcsec.sdlmp.auth.service.AuthService;

@Component
public class PlanScanner {
	private final static Logger logger = LoggerFactory.getLogger(PlanScanner.class);

	@Autowired
	AuthService service;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 20, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	@Scheduled(fixedRate = 1000)
	public void scheduled_scan() {
		List<Plan> planList = service.scheduled_scanPlan();

		if (planList == null || planList.isEmpty()) {
			return;
		}

		logger.info(planList.toString());

		LocalDateTime nowTime = LocalDateTime.now();

		char timeFlag;
		LocalDateTime nextTime = null;
		int hour = 0;
		int minute = 0;
		int period = 0;
		boolean isFirst = false;
		boolean isOverTime = false;

		for (Plan plan : planList) {
			isFirst = false;
			isOverTime = false;
			timeFlag = plan.getPeriod_flag().charAt(0);
			hour = Integer.valueOf(plan.getTime().substring(0, 2));
			minute = Integer.valueOf(plan.getTime().substring(3));
			period = Integer.valueOf(plan.getPeriod());

			// 取出上一执行时间
			if (plan.getNext_time() == null) {
				isFirst = true;
				nextTime = null;
			} else {
				try {
					nextTime = LocalDateTime.parse(plan.getNext_time(), formatter);
				} catch (Exception e) {
					logger.error("plan[{}] 的时间格式错误 ：{}", plan.getId(), plan.getNext_time());
					e.printStackTrace();
					continue;
				}
			}

			// 计算下一执行时间
			while (nextTime == null || nextTime.isBefore(nowTime)) {
				isOverTime = true;
				switch (timeFlag) {
				case '0':// 无周期
					nextTime = nowTime.minusDays(1).plusDays(1);
					break;
				case '1':// 天
					if (nextTime == null) {
						nextTime = nowTime.withHour(hour).withMinute(minute);
					} else {
						nextTime = nextTime.withHour(hour).withMinute(minute).plusDays(1);
					}
					break;
				case '2':// 周
					if (nextTime == null) {
						nextTime = turnWeek(nowTime.withHour(hour).withMinute(minute), period);
					} else {
						nextTime = nextTime.withHour(hour).withMinute(minute).plusWeeks(1);
					}
					break;
				case '3':// 月
					if (nextTime == null) {
						nextTime = setDayOfMonth(nowTime.withHour(hour).withMinute(minute), period);
					} else {
						nextTime = setDayOfMonth(nextTime.withHour(hour).withMinute(minute).plusMonths(1), period);
					}
					break;
				default:
					break;
				}
			}

			// 更新信息到数据库
			String state = null;
			if (plan.getImmediately_flag().equals("1") && timeFlag == '0') {
				state = "2";
			} else {
				state = plan.getState();
			}

			if (service.scheduled_setPlan(plan.getId(), "0", nextTime.format(formatter), state) == null) {
				logger.error("scheduled_setPlan err plan_id:{}  nextTime:{}", plan.getId(), nextTime.format(formatter));
				return;
			}

			// 执行任务
			if (("1".equals(plan.getImmediately_flag())) || (isOverTime && !isFirst)) {
				try {
					executor.execute(new ScheduledExecutor(service, plan));
				} catch (Exception e) {
					logger.error("Exception ", e);
					continue;
				}
			}
		}
	}

	private LocalDateTime setDayOfMonth(LocalDateTime nextTime, int month) {
		if (month <= 28) {
			return nextTime.withDayOfMonth(month);
		}
		int lastDaysOfNextMonth = nextTime.plusMonths(1).withDayOfMonth(1).minusDays(1).getDayOfMonth();
		if (month >= lastDaysOfNextMonth) {
			return nextTime.withDayOfMonth(lastDaysOfNextMonth);
		} else {
			return nextTime.withDayOfMonth(month);
		}
	}

	private LocalDateTime turnWeek(LocalDateTime nowDateTime, int week) {
		int todayWeek = nowDateTime.getDayOfWeek().getValue();
		if (todayWeek <= week) {
			todayWeek = todayWeek + 7 - week;
		} else {
			todayWeek = todayWeek - week;
		}
		return nowDateTime.minusDays(todayWeek);
	}
}
