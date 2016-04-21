package nj.common.sms.enums;

public enum ZHSmsError {
	USER_TASKID_ERROR("30001","请求任务存在"),USER_DATA_CONTENT_ERROR("30002","超过最大长度"),USER_DATA_RECEIVE_ERROR("30003","接收人号码有误"),
	USER_COMP_OK("30101","请求成功"),USER_COMP_ERROR("30102","短信服务失败"),
	COMP_HTTP_ERROR("30120","与第三方通讯异常"),COMP_PARSE_RESULT_ERROR("30121","组件解析第三方返回数据时错误");
	
	
	private String code;
	private String message;
	
	private ZHSmsError(String value,String name) {
		this.code=value;
		this.message=name;
	}
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}


	public String toString(){
		return super.toString()+String.format("(%s,%s)", code,message);
	}
}
