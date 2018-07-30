package cn.com.tcsec.sdlmp.common.filter;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.InvocationListener;

public class CommonInvocationListener implements InvocationListener {
	private final static Logger logger = LoggerFactory.getLogger(CommonInvocationListener.class);
	static long count = 1;
	ThreadLocal<Long> localCount = new ThreadLocal<Long>();

	@Override
	public void willInvoke(Method method, List<JsonNode> arguments) {
		synchronized (this) {
			localCount.set(count++);
		}
		logger.info("[{}] #########  开始:{} threadId：{}", localCount.get(), method.getName(),
				Thread.currentThread().getId());

		for (JsonNode node : arguments) {
			if (node.isNull()) {
				logger.info("[{}]arg:null", localCount.get());
			} else {
				logger.info("[{}]arg:{}", localCount.get(), node.toString());
			}
		}
	}

	@Override
	public void didInvoke(Method method, List<JsonNode> arguments, Object result, Throwable t, long duration) {
		if (result == null) {
			logger.info("[{}]result:{}", localCount.get(), null);
		} else {
			logger.info("[{}]result:{}", localCount.get(), result.toString());
		}
		logger.info("[{}] #########  结束:{} ms", localCount.get(), duration);
	}

}
