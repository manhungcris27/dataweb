package murach.email;

import java.io.*;
import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import murach.business.User;
import murach.data.UserDB;
import murach.util.*;
@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    getServletContext()
        .getRequestDispatcher("/index.jsp")
        .forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // perform action and set URL
        String url = "/index.jsp";

        if (action.equals("join")) {
            url = "/index.jsp";
        } 
        else if (action.equals("add")) {

            // get parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // store data in User object
            User user = new User(firstName, lastName, email);
            UserDB.insert(user);

            request.setAttribute("user", user);

            // send email to user
            String to = email;
            String from = "email_list@murach.com";
            String subject = "Welcome to our email list";

            String body = 
                    "Dear " + firstName + ",\n\n"
                    + "Thanks for joining our email list. "
                    + "We'll make sure to send "
                    + "you announcements about new products "
                    + "and promotions.\n\n"
                    + "Have a great day and thanks again!\n\n"
                    + "Kelly Slivkoff\n"
                    + "Mike Murach & Associates";

            boolean isBodyHTML = false;

            try {
                MailUtilLocal.sendMail(to, from, subject, body, isBodyHTML);

            } catch (MessagingException e) {

                String errorMessage =
                        "ERROR: Unable to send email.<br>"
                        + "Check Tomcat logs for details.<br>"
                        + "You may need to configure your system "
                        + "as described in chapter 14.<br>"
                        + "ERROR MESSAGE: " + e.getMessage();

                request.setAttribute("errorMessage", errorMessage);

                this.log(
                    "Unable to send email.\n"
                    + "TO: " + to + "\n"
                    + "FROM: " + from + "\n"
                    + "SUBJECT: " + subject + "\n"
                    + "BODY:\n" + body + "\n"
                );
            }

            url = "/thanks1.jsp";
        }

        // forward
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
