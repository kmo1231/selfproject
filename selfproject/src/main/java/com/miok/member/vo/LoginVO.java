package com.miok.member.vo;

public class LoginVO {
    private String userid;		//사용자id
    private String userpw;		//사용자비밀번호
    private String remember;	//
    
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
    
    public String getRemember() {
        return remember;
    }
    
    public void setRemember(String remember) {
        this.remember = remember;
    }

}
