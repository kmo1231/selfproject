package com.miok.member.vo;

import org.springframework.web.multipart.MultipartFile;

public class UserVO {
	private String userno;		//사용자번호
	private String userid;		//아이디
	private String userpw;		//비밀번호
	private String usernm;		//사용자명
	private String photo;		//사진
	private String userrole;	//사용자종류
	private String ip;			//ip
	private String deptno;		//부서번호
	private String deptnm;		//부서명
	
	private MultipartFile photoFile; //사진

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getUsernm() {
		return usernm;
	}

	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getDeptnm() {
		return deptnm;
	}

	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}

	public MultipartFile getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(MultipartFile photoFile) {
		this.photoFile = photoFile;
	}
	
}
