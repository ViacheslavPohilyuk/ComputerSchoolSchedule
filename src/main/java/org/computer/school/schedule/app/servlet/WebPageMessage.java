package org.computer.school.schedule.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mac on 22.02.17.
 */
public class WebPageMessage {
    private PrintWriter printPage;

    public WebPageMessage(HttpServletResponse resp) {
        try {
            printPage = resp.getWriter();
        }
            catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Method converts some object to the json-string and
     *  outputs one on the web page
     *  @param entity some object
     */
    public <T> void jsonMessage(T entity) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            message(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity));
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
        printPage.println(messageString);
    }
}
