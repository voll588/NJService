package nj.api.entity;


import java.util.Date;

public class Version {
    private Long versionId;

    private Integer vnum;

    private String vname;

    private Date vdate;

    private String vcontext;

    private String vaddress;

    private Integer isupdate;

    private Long vsize;

    private String vmd5;

    private String vtype;

    public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Integer getVnum() {
        return vnum;
    }

    public void setVnum(Integer vnum) {
        this.vnum = vnum;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public Date getVdate() {
        return vdate;
    }

    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    public String getVcontext() {
        return vcontext;
    }

    public void setVcontext(String vcontext) {
        this.vcontext = vcontext;
    }

    public String getVaddress() {
        return vaddress;
    }

    public void setVaddress(String vaddress) {
        this.vaddress = vaddress;
    }

    public Integer getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(Integer isupdate) {
        this.isupdate = isupdate;
    }

    public Long getVsize() {
        return vsize;
    }

    public void setVsize(Long vsize) {
        this.vsize = vsize;
    }

    public String getVmd5() {
        return vmd5;
    }

    public void setVmd5(String vmd5) {
        this.vmd5 = vmd5;
    }

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}


}