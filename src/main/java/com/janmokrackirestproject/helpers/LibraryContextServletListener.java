package com.janmokrackirestproject.helpers;

import com.janmokrackirestproject.beans.Book;
import com.janmokrackirestproject.beans.User;
import com.janmokrackirestproject.helpers.dataaccess.BookDbAccess;
import com.janmokrackirestproject.helpers.dataaccess.UserDbAccess;
import com.janmokrackirestproject.responses.ExceptionResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import org.w3c.dom.UserDataHandler;

import java.util.ArrayList;

@WebListener
public class LibraryContextServletListener implements ServletContextListener {



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BookDbAccess bookDbAccess = new BookDbAccess();
        ArrayList<Book> books = bookDbAccess.GetBooks();
        sce.getServletContext().setAttribute("books", books);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("books");
    }
}
