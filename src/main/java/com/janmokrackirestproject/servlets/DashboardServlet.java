package com.janmokrackirestproject.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.janmokrackirestproject.beans.Book;
import com.janmokrackirestproject.beans.Role;
import com.janmokrackirestproject.beans.User;
import com.janmokrackirestproject.helpers.EncryptionMethods;
import com.janmokrackirestproject.helpers.dataaccess.BookDbAccess;
import com.janmokrackirestproject.helpers.dataaccess.UserDbAccess;
import com.janmokrackirestproject.requests.AddBookRequest;
import com.janmokrackirestproject.requests.DeleteBookRequest;
import com.janmokrackirestproject.requests.LoginRequest;
import com.janmokrackirestproject.responses.*;
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

    private boolean ifAdmin(HttpServletRequest request){
       User user = (User) request.getSession().getAttribute("CurrentUser");
       return user.getRole()==Role.ADMIN;
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        BookDbAccess bookDbAccess = new BookDbAccess();
        Response responseObj = new BadRequestResponse("Unable to add specified book");
        try {
            if(ifAdmin(request)) {
                AddBookRequest addBookRequest =
                        gson.fromJson(request.getReader(),AddBookRequest.class);
                Book addedBook = bookDbAccess.AddBook(addBookRequest.getTitle(), addBookRequest.getAuthor(), addBookRequest.getYear());
                if (addedBook != null){
                    books.add(addedBook);
                    responseObj = new OKResponse(addedBook);
                }
            }
            else {
                responseObj = new UnauthorizedResponse("Only Admin can add books");
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        BookDbAccess bookDbAccess = new BookDbAccess();
        Response responseObj = new BadRequestResponse("Unable to delete specified book");
        try {
            if(ifAdmin(request)) {
                DeleteBookRequest deleteBookRequest =
                        gson.fromJson(request.getReader(), DeleteBookRequest.class);
                Book bookToDelete = books.get(deleteBookRequest.getId());
                if (bookToDelete != null) {
                    bookDbAccess.RemoveBook(bookToDelete.getTitle());
                    books.remove(deleteBookRequest.getId());
                    responseObj = new OKResponse("");
                }
            }
            else {
                responseObj = new UnauthorizedResponse("Only Admin can delete books");
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
