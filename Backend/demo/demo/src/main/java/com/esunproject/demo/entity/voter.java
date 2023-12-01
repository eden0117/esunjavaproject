package com.esunproject.demo.entity;

import org.json.JSONException;
import org.json.JSONObject;
//定義voter物件
public class voter {
    //voter物件內涵之變數
    private int id;
    private String name;
    private int permission;
    //實作voter物件
    public voter(int id,String name,int permission){
        super();
        this.id = id;
        this.name = name;
        this.permission = permission;
    }
    //由於變數private依靠function取得變數
    public int getVoterID(){return id;}
    public String getVoterName(){return name;}
    public int getVoterPermission(){return permission;}
    //以json格式回傳所有變數
    public JSONObject getData() throws JSONException {
        JSONObject jso = new JSONObject();
        jso.put("voterID", getVoterID());
        jso.put("VoterName", getVoterName());
        jso.put("voterPermission",  getVoterPermission());

        return jso;
    }
}


