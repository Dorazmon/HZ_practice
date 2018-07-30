package cn.com.tcsec.sdlmp.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessControl {

	boolean ip()

	default false;

	boolean session()

	default false;

	boolean method()

	default true;

	int[] paramIndex() default { 0 };

	boolean enable()

	default false;

	// 周期 - 秒
	int cycle()

	default 0;

	// 次数
	int count()

	default 0;

	// 超限后 的处理方式
	Handler handler() default Handler.reject;

	public enum Handler {
		reject, limit_ip, limit_session, auth_code
	}
}
