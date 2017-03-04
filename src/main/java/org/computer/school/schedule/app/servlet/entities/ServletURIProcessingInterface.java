package org.computer.school.schedule.app.servlet.entities;

/**
 * Created by mac on 22.02.17.
 */
public interface ServletURIProcessingInterface {
    /** Deleting entity in the database
     * In this method occurring the URI fragmentation
     *
     * Getting URI tokens
     * For partition some URI on tokens we use method that called <code>split</code>
     * of the String class with value of the parameter of this method  - "/".
     * The result of these operation is massive of String objects <code>pathTokens</code>
     *
     * Getting URI parameters
     * For getting parameters (values that are placed after symbol "?" in URI)
     * of URL we use getParameterMap method for HTTPServletRequest
     * object <code>req</code>, and put the result of this method
     * to the <code>Map<String, String[]> pathParams</code>
     *
     * Operations of servlets
     * Now we put <code>pathTokens</code> and <code>pathParms</code>
     * to the constructor of <code>CoursesServletOperations</code> object
     * or <code>PersonsServletOperations</code> object.
     * And retrieving method <code>EntityServletURIProcessing</code>
     * for use operation (read, insert, delete) of required entity
     */
    void URIProcessing();

    /** Main operations using
     * Using one of three operations Read, Insert and Delete
     * of either Course entity or Person entity.
     * If value of pathTokens[0] (URL-pattern of a servlet) equals
     * "courses" we will using methods of the <code>CoursesServletOperations</code>
     * class otherwise we use methods of the
     * <code>PersonsServletOperations</code> class
     */
    void choiceServletOperation();
}
