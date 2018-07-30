package cn.com.tcsec.sdlmp.common.param;

public class REGEX {
	
//	public static void main(String[] args) {
//		System.out.println(Pattern.compile(oauthChannel).matcher("osch").matches());
//		System.out.println(Pattern.compile(oauthToken).matcher("2cffc968-7cf9-4f6d-b607-35197012a1a1").matches());
//	}

	public final static String ckeckSqlInject = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)"; // 防止sql注入的正则表达式
	public final static String accessToken = "[a-zA-Z][a-zA-Z0-9_]{4,25}:[0-9a-f]{64}";
	public final static String oauthToken = "[a-zA-Z0-9_-]{4,250}";
	public final static String oauthChannel = "(osch)|(gith)";
	public final static String smscode = "[0-9]{6}";
	public final static String emailToken = "[a-zA-Z0-9]{20}";
	public final static String imageCode = "[a-zA-Z0-9]{6}";

	public final static String tableID = "[1-9][0-9]{0,10}";
	public final static String userID = "^[a-zA-Z][a-zA-Z0-9_]{4,25}";
	public final static String userName = "^[\\s\\S]{2,30}$";
	public final static String passwd = "[a-zA-Z0-9]{64}";
	public final static String phone = "^(1[3456789][1234567890])(\\d{8})$";
	public final static String email = "\\w+@(\\w+\\.){1,3}\\w+";
	public final static String role = "[0-3]";
	public final static String userState = "[1-2]";
	public final static String contact = "(" + userID + ")|(" + phone + ")";

	public final static String projectType = "[0-1]";
	public final static String projectState = "[0-9]";
	public final static String projectName = "[a-zA-Z0-9_-]{2,40}";
	// public final static String projectLanguage = "[a-z+#0-9]{1,10}";
	public final static String projectLanguage = "(java)|(php)";
	public final static String projectNumber = "[a-zA-Z0-9_-]{2,20}";
	public final static String projectDesc = "^[\\s\\S]{1,199}$";
	public final static String projectUrl = "^git@[[a-zA-Z0-9_\\-]{1,20}.]+[a-zA-Z0-9_\\-]{1,20}:[a-zA-Z0-9_\\-]{2,50}/[a-zA-Z0-9_\\-]{2,50}.git$";

	public final static String planTime = "(([0][0-9])|([1-2][0-9])|([3][0-1]))(([0-1][0-9])|([2][0-3])):[0-5][0-9]";
	public final static String planType = "[0-1]";
	public final static String planDesc = "^[\\s\\S]{1,199}$";
	public final static String immediatelyFlag = "[0-1]";
	public final static String planState = "([1])|([3])";
	public final static String timeFlag = "[0-3]";

	public final static String companyName = "^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{2,60}$";
	public final static String companyDesc = "^[\\s\\S]{1,199}$";
	public final static String companyUrl = "^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*\\'().&=+$%-]+: )"
			+ "?[0-9a-z_!~*\\'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-z_!~*\\'"
			+ "()-]+.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].[a-z]{2,6})(:[0-9]{1,4})?((/?"
			+ ")|(/[0-9a-z_!~*\\'().;?:@&=+$,%#-]+)+/?)$";

	public final static String sortContent = "[a-z_]{2,20}";
	public final static String currentPage = "[1-9][0-9]{0,4}";
	public final static String pageSize = "[1-9][0-9]{0,2}";
	public final static String resultData = "^[\\s\\S]{1,}$";
	public final static String riskType = "(total)|(high)|(middle)|(low)";
}
