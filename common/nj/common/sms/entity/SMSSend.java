package nj.common.sms.entity;

import java.util.Date;

public class SMSSend {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.taskid
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Integer taskid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.smscode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String smscode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.sendtime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Date sendtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.results
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String results;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.detail
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String detail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String handle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.account
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String account;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_send.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String extendtype;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.id
     *
     * @return the value of sms_send.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.id
     *
     * @param id the value for sms_send.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.taskid
     *
     * @return the value of sms_send.taskid
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Integer getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.taskid
     *
     * @param taskid the value for sms_send.taskid
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.smscode
     *
     * @return the value of sms_send.smscode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getSmscode() {
        return smscode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.smscode
     *
     * @param smscode the value for sms_send.smscode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setSmscode(String smscode) {
        this.smscode = smscode == null ? null : smscode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.sendtime
     *
     * @return the value of sms_send.sendtime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.sendtime
     *
     * @param sendtime the value for sms_send.sendtime
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.results
     *
     * @return the value of sms_send.results
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getResults() {
        return results;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.results
     *
     * @param results the value for sms_send.results
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setResults(String results) {
        this.results = results == null ? null : results.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.detail
     *
     * @return the value of sms_send.detail
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.detail
     *
     * @param detail the value for sms_send.detail
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.handle
     *
     * @return the value of sms_send.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getHandle() {
        return handle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.handle
     *
     * @param handle the value for sms_send.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setHandle(String handle) {
        this.handle = handle == null ? null : handle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.account
     *
     * @return the value of sms_send.account
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.account
     *
     * @param account the value for sms_send.account
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_send.extendtype
     *
     * @return the value of sms_send.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getExtendtype() {
        return extendtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_send.extendtype
     *
     * @param extendtype the value for sms_send.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setExtendtype(String extendtype) {
        this.extendtype = extendtype == null ? null : extendtype.trim();
    }
}