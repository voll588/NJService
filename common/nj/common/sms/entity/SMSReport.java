package nj.common.sms.entity;

public class SMSReport {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_report.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_report.detail
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String detail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_report.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String handle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_report.smscode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String smscode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_report.id
     *
     * @return the value of sms_report.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_report.id
     *
     * @param id the value for sms_report.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_report.detail
     *
     * @return the value of sms_report.detail
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_report.detail
     *
     * @param detail the value for sms_report.detail
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_report.handle
     *
     * @return the value of sms_report.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getHandle() {
        return handle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_report.handle
     *
     * @param handle the value for sms_report.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setHandle(String handle) {
        this.handle = handle == null ? null : handle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_report.smscode
     *
     * @return the value of sms_report.smscode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getSmscode() {
        return smscode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_report.smscode
     *
     * @param smscode the value for sms_report.smscode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setSmscode(String smscode) {
        this.smscode = smscode == null ? null : smscode.trim();
    }
}