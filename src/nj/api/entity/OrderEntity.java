package nj.api.entity;

import java.util.Date;

public class OrderEntity {

	
	private Long orderId;
	
	private Long psId;
	
	private Long userId;
	
	private Long serviceId;
	
	private Long amount;
	
	private String channel;
	
	private String orderSub;
	
	private Integer state;
	
	private Date orderTime;
	
	private Integer buyMonth;
	
	private Long terId;
	
	private String chargeId;
	
	private Integer orderType;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}



	public Long getPsId() {
		return psId;
	}

	public void setPsId(Long psId) {
		this.psId = psId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOrderSub() {
		return orderSub;
	}

	public void setOrderSub(String orderSub) {
		this.orderSub = orderSub;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return orderTime;
	}

	public void setCreateTime(Date createTime) {
		this.orderTime = createTime;
	}

	public Integer getBuyMonth() {
		return buyMonth;
	}

	public void setBuyMonth(Integer buyMonth) {
		this.buyMonth = buyMonth;
	}

	public Long getTerId() {
		return terId;
	}

	public void setTerId(Long terId) {
		this.terId = terId;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
}
