package nj.common.sms.enums;

public enum SmsPriority {
	
	ONE("1","缓慢"),TWO("2","普通"),THREE("3","紧急");
	
	private String value;
	private String name;
	
	private SmsPriority(String value,String name) {
		this.value=value;
		this.name=name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return super.toString()+String.format("(%s,%s)", value,name);
	}
}
