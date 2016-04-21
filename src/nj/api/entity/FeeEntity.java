package nj.api.entity;

import java.util.Date;

public class FeeEntity {

	//单价id
	private Long psId;
	
	//单价名称
	private String psName;
	
	//价格（分）
	private Long psPrice;
	
	//时间
	private Date psTime;
	
	//状态
	private Integer psState;
	
	//类型
	private String psType;
	

	public Long getPsId() {
		return psId;
	}

	public void setPsId(Long psId) {
		this.psId = psId;
	}

	public String getPsName() {
		return psName;
	}

	public void setPsName(String psName) {
		this.psName = psName;
	}

	public Long getPsPrice() {
		return psPrice;
	}

	public void setPsPrice(Long psPrice) {
		this.psPrice = psPrice;
	}

	public Date getPsTime() {
		return psTime;
	}

	public void setPsTime(Date psTime) {
		this.psTime = psTime;
	}

	public Integer getPsState() {
		return psState;
	}

	public void setPsState(Integer psState) {
		this.psState = psState;
	}

	public String getPsType() {
		return psType;
	}

	public void setPsType(String psType) {
		this.psType = psType;
	}
	
	
	
	
}
