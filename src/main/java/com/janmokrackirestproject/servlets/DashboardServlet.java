package com.janmokrackirestproject.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.janmokrackirestproject.beans.Book;
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
import java.util.ArrayList;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    ArrayList<Book> books;
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        books = (ArrayList<Book>) context.getAttribute("books");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        Response responseObj = new OKResponse("[]");
        try {
            responseObj = new OKResponse(books);
        }
        catch (Exception e) {
            responseObj = new ExceptionResponse(e.getLocalizedMessage());
        }
        finally {
            response.setStatus(responseObj.getStatusCode());
            gson.toJson(responseObj, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
