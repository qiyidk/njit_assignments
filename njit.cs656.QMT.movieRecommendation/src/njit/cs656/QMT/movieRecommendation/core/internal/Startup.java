package njit.cs656.QMT.movieRecommendation.core.internal;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

/**
 * <p>
 * ServletListener listen server startup event and initiate environment
 * </p>
 *
 * @author qiyi
 * @version 2015-11-30
 */
public class Startup extends HttpServlet implements ServletContextListener {

    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        new ServerInitiate().init();
    }
}
