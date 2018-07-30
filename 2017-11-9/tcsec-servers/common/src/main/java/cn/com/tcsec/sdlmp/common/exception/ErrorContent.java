package cn.com.tcsec.sdlmp.common.exception;

import com.googlecode.jsonrpc4j.ErrorResolver.JsonError;

public class ErrorContent extends JsonError {

	// 系统错误类(32003 ~ 32009)
	/**
	 * 系统错误
	 */
	public static final JsonError SERVER_ERROR = new JsonError(-32004, "server error", null);

	// 功能性拒绝(32010 ~ 32049)
	/**
	 * 请求过于频繁，系统会对一些接口进行频率控制，频率太快会被拒绝
	 */
	public static final JsonError REQUEST_TOO_OFTEN = new JsonError(-32010, "request too often", null);
	/**
	 * 请求超限，系统会对一些接口的调用次数做限制
	 */
	public static final JsonError REQUEST_OVER_LIMIT = new JsonError(-32011, "request over limit", null);
	/**
	 * 资源状态异常
	 */
	public static final JsonError STATUS_EXCEPTION = new JsonError(-32012, "status exception", null);
	/**
	 * 系统拒绝，所有未定义的功能性拒绝
	 */
	public static final JsonError NO_PHONE = new JsonError(-32044, "without phone number", null);

	public static final JsonError SYSTEM_REJECT = new JsonError(-32045, "system reject", null);

	public static final JsonError CODE_REJECT = new JsonError(-32046, "auth code reject", null);
	/**
	 * 不存在的用户或者密码错误
	 */
	public static final JsonError FAILED_ACCESS = new JsonError(-32049, "failed access", null);

	/**
	 * 添加的资源已经存在
	 */
	public static final JsonError DUPLICATE_TARGET = new JsonError(-32047, "Duplicate Target", null);

	/**
	 * 其他错误,所有未定义的错误
	 */
	public static final JsonError OTHER_ERROR = new JsonError(-32099, "other error", null);

	public ErrorContent(int code, String message, Object data) {
		super(code, message, data);
	}
}
