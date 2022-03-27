package com.janmokrackirestproject.servlets;

import com.google.gson.Gson;
import com.janmokrackirestproject.beans.User;
import com.janmokrackirestproject.helpers.EncryptionMethods;
import com.janmokrackirestproject.helpers.dataaccess.UserDbAccess;
import com.janmokrackirestproject.requests.LoginRequest;
import com.janmokrackirestproject.responses.BadRequestResponse;
import com.janmokrackirestproject.responses.ExceptionResponse;
import com.janmokrackirestproject.responses.OKResponse;
import com.janmokrackirestproject.responses.Response;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        UserDbAccess userDbAccess = new UserDbAccess();
        Response responseObj = new OKResponse("Successfully logged in");
        try{
            LoginRequest loginRequest =
                    gson.fromJson(request.getReader(),LoginRequest.class);
            User user = userDbAccess.GetUser(loginRequest);
            if(user == null) {
                responseObj = new BadRequestResponse("Wrong login or password");
            }
            else {
                request.getSession().setAttribute("CurrentUser", user);
                response.addCookie(new Cookie("userId", EncryptionMethods.getBase64FromString(user.getLogin())));
            }
        }
        catch (Exception e) {
            responseObj = new ExceptionResponse(e.getLocalizedMessage());
        }
        finally {
            response.setStatus(responseObj.getStatusCode());
            gson.toJson(responseObj, response.getWriter());
        }
    }
}
