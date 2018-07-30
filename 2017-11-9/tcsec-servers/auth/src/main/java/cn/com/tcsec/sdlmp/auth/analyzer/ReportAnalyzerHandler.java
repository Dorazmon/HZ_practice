package cn.com.tcsec.sdlmp.auth.analyzer;

// public class ReportAnalyzerHandler implements InitializingBean,
// ApplicationContextAware {
// private ApplicationContext applicationContext;
// private List<ReportAnalyzer> reportAnalyzers;

// public void run() {
// for (ReportAnalyzer reportAnalyzer : reportAnalyzers) {
// if (reportAnalyzer.shoudRun() != null) {
// resultList = reportAnalyzer.analyze(scheduledTask.getTask_id(),
// scheduledTask.getProject_id(),
// resultList, srcDir);
// logger.info("project:{} task_id:{} analyzeCount:{}",
// scheduledTask.getProject_name(),
// scheduledTask.getTask_id(), (resultList == null) ? 0 : resultList.size());
// }
// }
// }

// @Override
// public void setApplicationContext(ApplicationContext applicationContext)
// throws BeansException {
// this.applicationContext = applicationContext;
// }
//
// @Override
// public void afterPropertiesSet() throws Exception {
// if (applicationContext == null) {
// throw new Exception("applicationContext 为空");
// }
// Map<String, ReportAnalyzer> map =
// applicationContext.getBeansOfType(ReportAnalyzer.class);
//
// for (Entry<String, ReportAnalyzer> entry : map.entrySet()) {
// if (reportAnalyzers == null) {
// reportAnalyzers = new ArrayList<>();
// }
// reportAnalyzers.add(entry.getValue());
// }
//
// if (reportAnalyzers.isEmpty()) {
// throw new Exception("没有 ReportAnalyzer 的实现 Bean");
// }
//
// reportAnalyzers.sort(new Comparator<ReportAnalyzer>() {
// @Override
// public int compare(ReportAnalyzer o1, ReportAnalyzer o2) {
// return o1.order() - o2.order();
// }
// });
// }
// }
