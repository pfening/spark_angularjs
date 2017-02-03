/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfg.spark;

import java.util.Collection;

/**
 *
 * @author PFENIGA1
 */
public interface UserService {
  
    public void addUser (User user);
     
    public Collection<User> getUsers ();
    public User getUser (String id);
     
    public User editUser (User user) throws UserException;
     
    public void deleteUser (String id);
     
    public boolean userExist (String id);
}