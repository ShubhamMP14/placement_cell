package com.example.project_patt;

import java.io.Serializable;
public class Jobdetails implements Serializable {
    private String job1;
    private String sal;
    private String com;
    private String loc;
    private String exp;
    private String high;
    private String desc;

    public Jobdetails(){
    }

    public Jobdetails(String loc,String sal,String exp,String high,String desc,String job1,String com){
        this.job1 = job1;
        this.sal = sal;
        this.com = com;
        this.loc = loc;
        this.exp=exp;
        this.high=high;
        this.desc=desc;
    }

    public String getLoc(){return loc;}
    public void setLoc(String loc){this.loc=loc;}
    public String getSal(){return sal;}

    public void setSal(String sal){this.sal=sal;}
    public String getJob1() {
        return job1;
    }

    public void setJob1(String job1) {
        this.job1 = job1;
    }
    public String getcom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getExp(){return exp;}
    public void setExp(String exp){this.exp=exp;}

    public String getHigh(){return high;}
    public void setHigh(String high){this.high=high;}

    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc=desc;}




}
