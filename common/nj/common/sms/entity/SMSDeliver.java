package nj.common.sms.entity;

import java.util.Date;

public class SMSDeliver {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.phone
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.content
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.sendtime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Date sendtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.receipttime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Date receipttime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String handle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.account
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_deliver.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String extendtype;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.id
     *
     * @return the value of sms_deliver.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.id
     *
     * @param id the value for sms_deliver.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.phone
     *
     * @return the value of sms_deliver.phone
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.phone
     *
     * @param phone the value for sms_deliver.phone
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.content
     *
     * @return the value of sms_deliver.content
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.content
     *
     * @param content the value for sms_deliver.content
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.sendtime
     *
     * @return the value of sms_deliver.sendtime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.sendtime
     *
     * @param sendtime the value for sms_deliver.sendtime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.receipttime
     *
     * @return the value of sms_deliver.receipttime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Date getReceipttime() {
        return receipttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.receipttime
     *
     * @param receipttime the value for sms_deliver.receipttime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setReceipttime(Date receipttime) {
        this.receipttime = receipttime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.handle
     *
     * @return the value of sms_deliver.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getHandle() {
        return handle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.handle
     *
     * @param handle the value for sms_deliver.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setHandle(String handle) {
        this.handle = handle == null ? null : handle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.account
     *
     * @return the value of sms_deliver.account
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.account
     *
     * @param account the value for sms_deliver.account
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_deliver.extendtype
     *
     * @return the value of sms_deliver.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getExtendtype() {
        return extendtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_deliver.extendtype
     *
     * @param extendtype the value for sms_deliver.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setExtendtype(String extendtype) {
        this.extendtype = extendtype == null ? null : extendtype.trim();
    }
}