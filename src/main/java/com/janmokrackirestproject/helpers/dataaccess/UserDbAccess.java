package com.janmokrackirestproject.helpers.dataaccess;

import com.janmokrackirestproject.beans.Role;
import com.janmokrackirestproject.beans.User;
import com.janmokrackirestproject.helpers.EncryptionMethods;
import com.janmokrackirestproject.requests.LoginRequest;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserDbAccess extends DbAccess {

    public User GetUser(LoginRequest request) {
        try {
            String hashedPass = EncryptionMethods.HashPassword(request.getPassword());
            Statement statement = GetConnection().createStatement();
            String query = "SELECT * FROM Users WHERE login = '" + request.getUsername() +"';";
            ResultSet result = statement.executeQuery(query);
            String login = result.getString("login");
            String pass = result.getString("pass");
            int role = result.getInt("role");
            Role roleval = Role.values()[role];
            if (request.getUsername().equals(login) && hashedPass.equals(pass)) {
                User user = new User(login, pass, roleval);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}