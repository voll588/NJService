package nj.common.sms.entity;

import java.util.Date;

public class SMSTask {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.taskcode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String taskcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.content
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.phones
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String phones;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.submitdate
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private Date submitdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.priority
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String priority;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String handle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_task.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    private String extendtype;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.id
     *
     * @return the value of sms_task.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.id
     *
     * @param id the value for sms_task.id
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.taskcode
     *
     * @return the value of sms_task.taskcode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getTaskcode() {
        return taskcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.taskcode
     *
     * @param taskcode the value for sms_task.taskcode
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode == null ? null : taskcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.content
     *
     * @return the value of sms_task.content
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.content
     *
     * @param content the value for sms_task.content
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.phones
     *
     * @return the value of sms_task.phones
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getPhones() {
        return phones;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.phones
     *
     * @param phones the value for sms_task.phones
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setPhones(String phones) {
        this.phones = phones == null ? null : phones.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.submitdate
     *
     * @return the value of sms_task.submitdate
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public Date getSubmitdate() {
        return submitdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.submitdate
     *
     * @param submitdate the value for sms_task.submitdate
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setSubmitdate(Date submitdate) {
        this.submitdate = submitdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.priority
     *
     * @return the value of sms_task.priority
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getPriority() {
        return priority;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.priority
     *
     * @param priority the value for sms_task.priority
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setPriority(String priority) {
        this.priority = priority == null ? null : priority.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.handle
     *
     * @return the value of sms_task.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getHandle() {
        return handle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.handle
     *
     * @param handle the value for sms_task.handle
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setHandle(String handle) {
        this.handle = handle == null ? null : handle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sms_task.extendtype
     *
     * @return the value of sms_task.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public String getExtendtype() {
        return extendtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sms_task.extendtype
     *
     * @param extendtype the value for sms_task.extendtype
     *
     * @mbggenerated Wed Jun 05 16:39:03 CST 2013
     */
    public void setExtendtype(String extendtype) {
        this.extendtype = extendtype == null ? null : extendtype.trim();
    }
}