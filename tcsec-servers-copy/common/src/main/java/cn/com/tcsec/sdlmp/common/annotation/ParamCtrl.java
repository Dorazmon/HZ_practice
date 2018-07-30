package cn.com.tcsec.sdlmp.common.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface ParamCtrl {

	/**
	 * 是否为必输字段
	 * 
	 * @return
	 */
	public boolean must() default true;

	/**
	 * 该字段的正则
	 * 
	 * @return
	 */
	public String regex();

	/**
	 * 是否检查sql注入
	 * 
	 * @return
	 */
	public boolean ckeckSqlInject() default false;

}
