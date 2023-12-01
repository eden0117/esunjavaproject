package com.esunproject.demo.entity;

import org.json.JSONException;
import org.json.JSONObject;
//定義voteitem物件
public class voteitem {
    //voteitem物件內涵之變數
    private int id;
    private String name;
    private int count;
    private int status;
    //實作voteitem物件
    public voteitem(int id,String name,int count,int status){
        super();
        this.id = id;
        this.name=name;
        this.count=count;
        this.status=status;
    }
    //由於變數private依靠function取得變數
    public int getVoteID(){return id;}
    public String getVoteName(){return name;}
    public int getVoteCount(){return count;}
    public int getVoteStatus(){return status;}
    //以json格式回傳所有變數
    public JSONObject getData() throws JSONException {
        JSONObject jso = new JSONObject();
        jso.put("voteID", getVoteID());
        jso.put("VoteName", getVoteName());
        jso.put("voteCount", getVoteCount());
        jso.put("voteStatus",  getVoteStatus());

        return jso;
    }
}
