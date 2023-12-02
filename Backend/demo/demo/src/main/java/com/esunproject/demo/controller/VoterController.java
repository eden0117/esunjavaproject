package com.esunproject.demo.controller;

import com.esunproject.demo.entity.voter;
import com.esunproject.demo.share.DBMgr;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class VoterController {
    //實作登入功能
    @PostMapping("/Login")
    public ResponseEntity<?> Login(@RequestBody String jsoninfo) throws JSONException {
        voter v = null;
        JsonElement jsonElement = JsonParser.parseString(jsoninfo);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //存放給前端的內容
        JSONObject response = new JSONObject();
        //取得內容
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL UserLogin(?,?)}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                //填入variable
                stmt.setString(1, username);
                stmt.setString(2, password);
                //實際執行 (db開啟autocommit 不用再commit一次)
                ResultSet rs = stmt.executeQuery();
                //如果有下一個結果
                if(rs.next()) {
                    //建立新的 voter 物件
                     v = new voter(
                             rs.getInt("id"),
                             rs.getString("username"),
                             rs.getInt("userpermission"));
                }
                //取得id
                int id = v.getVoterID();
                //如果用戶不存在會回傳0
                if (id != 0){
                    //建立response
                    response.put("StatusCode", "200");
                    response.put("Message", "SUCCESS !!");
                    response.put("data", v.getData());
                    return ResponseEntity.ok().body(response.toString());
                }else{
                    //建立response
                    response.put("StatusCode", "400");
                    response.put("Message", "Login Unsuccessfuly");
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
    //實作登出功能
    @PostMapping("/Logout")
    public ResponseEntity<?> Logout(@RequestBody String jsoninfo) throws JSONException {

        JsonElement jsonElement = JsonParser.parseString(jsoninfo);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //存放給前端的內容
        JSONObject response = new JSONObject();
        //取得內容
        int userid = jsonObject.get("userid").getAsInt();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL UserLogout(?)}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                //填入variable
                stmt.setInt(1, userid);
                //實際執行 (db開啟autocommit 不用再commit一次)
                stmt.executeUpdate();
                //若成功更新(僅會更新一個使用者的狀態，少於或多於皆會失敗)
                if (stmt.getUpdateCount() == 1) {
                    //建立response
                    response.put("StatusCode", "200");
                    response.put("Message", "Logout Successfuly");
                    return ResponseEntity.ok().body(response.toString());
                }else{
                    //建立response
                    response.put("StatusCode", "400");
                    response.put("Message", "Logout Unsuccessfuly");
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
    //實作註冊功能
    @PostMapping("/Signup")
    public ResponseEntity<?> Signup(@RequestBody String jsoninfo) throws JSONException {
        JsonElement jsonElement = JsonParser.parseString(jsoninfo);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //存放給前端的內容
        JSONObject response = new JSONObject();
        //取得內容
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        //初始化資料庫連線，try ...with在結束時會自動關閉資源，不用close db 連線
        try (Connection conn = DBMgr.getConnection()) {
            //定義等一下要執行的sp名稱
            String sql = "{CALL CreateUser(?,?,?)}";
            //準備執行指定sp
            try (CallableStatement stmt = conn.prepareCall(sql)) {
                //填入variable
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.registerOutParameter(3, Types.INTEGER);  // 註冊輸出參數
                //實際執行 (db開啟autocommit 不用再commit一次)
                stmt.executeUpdate();
                int result = stmt.getInt(3);
                //若成功更新(僅會更新一個使用者的狀態，少於或多於皆會失敗)
                if (result == 1) {
                    //建立response
                    response.put("StatusCode", "200");
                    response.put("Message", "Signup Successfuly");
                    return ResponseEntity.ok().body(response.toString());
                }else{
                    //建立response
                    response.put("StatusCode", "400");
                    response.put("Message", "User Exist!!");
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

}
