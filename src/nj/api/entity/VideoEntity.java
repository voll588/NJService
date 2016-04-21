package nj.api.entity;

import java.util.Date;

public class VideoEntity {

	//
	private Long videoId;
	
	//班级名称
	private String videoName;
	
	//老师id
	private String videoDesc;
	
	//学生人数
	private String videoPic;
	
	//创建时间
	private Integer videoState;
	
	//班级
	private String videoUrl;
	
	//
	private Integer videoType;

	
	public Long getVideoId() {
		return videoId;
	}

	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}

	public String getVideoPic() {
		return videoPic;
	}

	public void setVideoPic(String videoPic) {
		this.videoPic = videoPic;
	}

	public Integer getVideoState() {
		return videoState;
	}

	public void setVideoState(Integer videoState) {
		this.videoState = videoState;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getVideoType() {
		return videoType;
	}

	public void setVideoType(Integer videoType) {
		this.videoType = videoType;
	}

	
}
