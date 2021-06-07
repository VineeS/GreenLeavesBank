package hu.edu.greenleaves.bank.servlet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Spy;

import hu.edu.greenleaves.bank.servlet.DefaultServlet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;

import java.io.IOException;

public class DefaultTestForServlet {

    private DefaultServlet defaultServlet;
    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;

    @Before
    public void run() {
        defaultServlet = new DefaultServlet();
        request = mock(HttpServletRequest.class, Mockito.RETURNS_DEEP_STUBS);
        session = mock(HttpSession.class);
        response = mock(HttpServletResponse.class, Mockito.RETURNS_DEEP_STUBS);
    }

    @Test
    public void sendError() {
        String msg = "error";
        when(request.getSession()).thenReturn(session);
        defaultServlet.sendError(request, msg);
        verify(request.getSession(), times(1)).setAttribute("req_error",msg);

    }

    @Test
    public void getUserId() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user_id")).thenReturn(13);

        // calling function to test
        int result = defaultServlet.getUserId(request);
        assertEquals(result, 13);
    }

    @Test
    public void setUserId() {
        int user_id = 13;
        when(request.getSession()).thenReturn(session);
        defaultServlet.setUserId(request, user_id);
        verify(request, times(1)).getSession(false);
        verify(request.getSession(false), times(1)).setAttribute("user_id", user_id);
    }



    @Test
    public void getPath() {
        // calling function to test
        String result = defaultServlet.getPath("loginpage");
        assertEquals("WEB-INF/jsploginpage.jsp", result);
    }

    @Test
    public void getRedirectPath() {
        // calling function to test
        String result = defaultServlet.getRedirectPath("loginpage");
        assertEquals("/greenleavesbankloginpage", result);
    }

}