/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfg.spark;

import com.google.gson.Gson;
import database.DataDAO;
import database.Database;
import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
       
        Gson gson = new Gson();
        get("/hello/:msg/:name", (request, response) -> new MyMessage(request.params(":msg"),request.params(":name")), gson::toJson);
        
        DataDAO d=new DataDAO(); 
        Database.getInstance().SqliteConnect();  
        get("/db", (request, response) -> new DataDAO().readAll(), gson::toJson);
        Database.getInstance().SqliteDisconnect();

    }
}