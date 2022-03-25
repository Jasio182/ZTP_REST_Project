package com.janmokrackirestproject.helpers.dataaccess;

import com.janmokrackirestproject.helpers.PasswordHashing;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class UserDbAccess extends DbAccess {

    public boolean UserExists(String username, String password) {
        try {
            String hashedPass = PasswordHashing.HashPassword(password);
            Statement statement = GetConnection().createStatement();
            String query = "SELECT * FROM Users WHERE login = '" + username +"';";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                if (username.equals(result.getString("login")) && hashedPass.equals(result.getString("pass")))
                    return true;
                else
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<String, String> FillUsersMap()
    {
        try {
            Statement statement = GetConnection().createStatement();
            String query = "SELECT * FROM Users";
            ResultSet result = statement.executeQuery(query);
            HashMap<String, String> users = new HashMap<String, String>();
            while (result.next()) {
                users.put(result.getString("login"), result.getString("pass"));
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, String>();
        }
    }
}