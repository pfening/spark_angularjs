package com.pfg.spark;

import com.google.gson.Gson;
import database.DataBean;
import database.DataDAO;
import database.Database;
import java.util.List;
import spark.Request;
import spark.Response;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
      
        Gson gson = new Gson();
        get("/hello/:msg/:name", (req, res) -> new MyMessage(req.params(":msg"),req.params(":name")), gson::toJson);
        get("/hi", (request, response) -> "Hello World", gson::toJson);
        DataDAO d=new DataDAO();        
        Database.getInstance().SqliteConnect();         
        List<DataBean> all = d.readAll();
        get("/all", (req, res) -> all, gson::toJson);        
        Database.getInstance().SqliteDisconnect();
        
        final UserService userService = new UserServiceMapImpl();
        
        post("/users", (request, response) -> {
            response.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            userService.addUser(user);

            return new Gson()
              .toJson(new StandardResponse(StatusResponse.SUCCESS));
        });
        
        get("/users", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS,new Gson().toJsonTree(userService.getUsers())));
        });
        
        get("/users/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS,new Gson()
                .toJsonTree(userService.getUser(request.params(":id")))));
        });
        
        put("/users/:id", (request, response) -> {
            response.type("application/json");
            User toEdit = new Gson().fromJson(request.body(), User.class);
            User editedUser = userService.editUser(toEdit);

            if (editedUser != null) {
                return new Gson().toJson(
                  new StandardResponse(StatusResponse.SUCCESS,new Gson()
                    .toJsonTree(editedUser)));
            } else {
                return new Gson().toJson(
                  new StandardResponse(StatusResponse.ERROR,new Gson()
                    .toJson("User not found or error in edit")));
            }
        });
        
        delete("/users/:id", (request, response) -> {
            response.type("application/json");
            userService.deleteUser(request.params(":id"));
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
        });
        
        options("/users/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS, 
                (userService.userExist(
                  request.params(":id"))) ? "User exists" : "User does not exists" ));
        });
       
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