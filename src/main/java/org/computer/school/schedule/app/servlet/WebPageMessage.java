package org.computer.school.schedule.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mac on 22.02.17.
 */
public class WebPageMessage {
    private HttpServletResponse resp;

    public WebPageMessage(HttpServletResponse resp) {
        this.resp = resp;
    }

    /** Method converts some object to the json-string and
     *  outputs one on the web page
     *  @param entity some object
     */
    public <T> void jsonMessage(T entity) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            message(objectMapper.writeValueAsString(entity));
        }
        catch (IOException e) {
            message("Converting to json error!");
            e.printStackTrace();
        }
    }

    /** Show some message on the web page
     *  @param messageString text that will be shown
     */
    public void message(String messageString) {
        try {
            resp.getWriter().println(messageString);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
