package com.pfg.spark;

import com.google.gson.Gson;
import database.DataBean;
import database.DataDAO;
import database.Database;
import java.util.List;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
      
        Gson gson = new Gson();
        get("/hello/:msg/:name", (req, res) -> new MyMessage(req.params(":msg"),req.params(":name")), gson::toJson);
       
        DataDAO d=new DataDAO();
        Database.getInstance().SqliteConnect(); 
        List<DataBean> all = d.readAll();
        get("/all", (req, res) -> all, gson::toJson);
       
 
        get("/read/:id", (Request req, Response res) ->{String id = req.params(":id"); String one = gson.toJson(d.readOne(id));System.err.println(id+" / "+one); return one;}, gson::toJson);

        Database.getInstance().SqliteDisconnect();
       
        /*
                DataDAO d=new DataDAO();
        Database.getInstance().SqliteConnect(); 
        DataBean all = d.readOne("1");
        System.err.println(all);
        Gson gson = new Gson();
        String json = gson.toJson(d.readOne("1"));
        System.out.println(json);
        Database.getInstance().SqliteDisconnect();
        */

    }
}