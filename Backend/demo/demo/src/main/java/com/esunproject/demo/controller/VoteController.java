package com.esunproject.demo.controller;

import com.esunproject.demo.entity.voteitem;
import com.esunproject.demo.share.DBMgr;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

@RestController
//避免cross協議導致無法前後端無法連線
@CrossOrigin(origins = "*")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    // 取得所有投票項目
    @GetMapping("/getvote")
    public ResponseEntity<?> getVote() throws JSONException {

        JSONArray voteItems = new JSONArray();
        JSONObject response = new JSONObject();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL GetAllVoteItems()}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql);
                 //實際執行 (db開啟autocommit 不用再commit一次)
                 ResultSet rs = stmt.executeQuery()) {
                //如果有下一個結果
                while (rs.next()) {
                    //建立voteitem物件
                    voteitem voteItem = new voteitem(
                            rs.getInt("idvote"),
                            rs.getString("voteName"),
                            rs.getInt("voteCount"),
                            rs.getInt("voteStatus")
                    );
                    //將 voteitem 物件放到 json array 當中，為了處理multi-select的情況
                    voteItems.put(voteItem.getData());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            //建立response
            response.put("StatusCode", "400");
            response.put("Message", "Database error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        //建立response
        response.put("StatusCode", "200");
        response.put("Message", "SUCCESS !!");
        response.put("data", voteItems);
        return ResponseEntity.ok().body(response.toString());
    }
    //新增投票項目 (待實作)
    @PostMapping("/setvote")
    public ResponseEntity<String> setVote(@RequestBody String jsoninfo) throws JSONException {
        //存放給前端的內容
        JSONObject response = new JSONObject();
        JsonElement jsonElement = JsonParser.parseString(jsoninfo);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //取得內容
        String votename = jsonObject.get("votename").getAsString();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL CreateVote(?)}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                //填入variable
                stmt.setString(1, votename);
                //實際執行 (db開啟autocommit 不用再commit一次)
                stmt.executeUpdate();
                //建立response
                response.put("StatusCode", "200");
                response.put("Message", "voteitem created successfully");
                return ResponseEntity.ok().body(response.toString());
            }
        } catch (SQLException | ClassNotFoundException e) {
            //建立response
            response.put("StatusCode", "500");
            response.put("Message", "Database error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response.toString());
        }
    }
    //實作刪除投票項目(使用更新實作，因關聯式資料庫中容易因delete意外破壞到關聯性，通常會以狀態呈現)
    @PostMapping("/deletevote")
    public ResponseEntity<String> deleteVote(@RequestBody String jsoninfo) throws JSONException {
        //存放給前端的內容
        JSONObject response = new JSONObject();
        JsonElement jsonElement = JsonParser.parseString(jsoninfo);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //取得內容
        int voteid = jsonObject.get("voteid").getAsInt();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL UpdateVote(?)}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                //填入variable
                stmt.setInt(1, voteid);  // 設置 votename 參數
                //實際執行 (db開啟autocommit 不用再commit一次)
                stmt.executeUpdate();
                //若成功更新(僅會更新一個投票的狀態，少於或多於皆會失敗)
                if (stmt.getUpdateCount() == 1) {
                    //建立response
                    response.put("StatusCode", "200");
                    response.put("Message", "voteitem deleted successfully");
                    return ResponseEntity.ok().body(response.toString());
                } else {
                    //建立response
                    response.put("StatusCode", "400");
                    response.put("Message", "The voteitem you want to deleted not found");
                    return ResponseEntity.badRequest().body(response.toString());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            //建立response
            response.put("StatusCode", "500");
            response.put("Message", "Database error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response.toString());
        }
    }

    //實作使用者投票
    @PostMapping("/uservote")
    public ResponseEntity<String> userVote(@RequestBody String voteid_j) throws JSONException, SQLException, ClassNotFoundException {
        //存放給前端的內容
        JSONObject response = new JSONObject();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL UserVote(?,?)}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                JsonElement jsonElement = JsonParser.parseString(voteid_j);
                //如果是json array形式(格式正確)
                if (jsonElement.isJsonArray()) {
                    //遍歷並一個一個更新
                    for (JsonElement item : jsonElement.getAsJsonArray()) {
                        JsonObject jsonObject = item.getAsJsonObject();
                        //取得內容
                        int voteid = jsonObject.get("voteid").getAsInt();
                        int voterid = jsonObject.get("voterid").getAsInt();
                        //填入variable
                        stmt.setInt(1, voteid);
                        stmt.setInt(2, voterid);
                        //實際執行 (db開啟autocommit 不用再commit一次)
                        stmt.executeUpdate();
                    }
                    //建立response
                    response.put("StatusCode", "200");
                    response.put("Message", "Vote successfully recorded");
                    return ResponseEntity.ok().body(response.toString());
                }
                else{
                    //建立response
                    response.put("StatusCode", "400");
                    response.put("Message", "Vote unsuccessful");
                    return ResponseEntity.badRequest().body(response.toString());
                }
            } catch (SQLException e) {
                //建立response
                response.put("StatusCode", "500");
                response.put("Message", "Database error: " + e.getMessage());
                return ResponseEntity.badRequest().body(response.toString());
            }
        }
    }
}
