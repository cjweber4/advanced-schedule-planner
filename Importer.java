
///////////////////////////////////////////////////////////////////////////////
// Main Class:       AdvancedSchedulePlanner
// File:             Importer
//
// Author:           Curtis Weber
// Email:            cjweber4@wisc.edu
// Date:         	 6/2016
///////////////////////////////////////////////////////////////////////////////
import javax.faces.bean.ManagedBean;
import javax.faces.bean.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.eclipse.persistence.sessions.server.ServerSession;

import java.sql.Connection;
import java.io.IOException;

/** Class to connect to user interface and html */
@ManagedBean
@RequestScoped
public class Importer extends HttpServlet {

	public static int hitCount = 0;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		hitCount++;
		resp.getWriter().print(hitCount);
		try {
			String[] coursesToTake = req.getParameterValues("addCourse");
			String opt = req.getParameter("OptType");
			String[] optimizedSchedule = {};

			if (opt.equals("Sleep In")) {
				optimizedSchedule = FindLatestSchedule.findLatestSchedule(coursesToTake);
			}
			resp.getWriter().print(hitCount);

			String[][] schedule = ScheduleToPrint.scheduleToPrint(optimizedSchedule);
			for (int p = 0; p < schedule[0].length; p++) {
				for (int j = 0; j < schedule.length; j++) {
					for (int t = schedule[j][p].length(); t < 12; t++) {
						schedule[j][p] = schedule[j][p] + " ";
					}
					resp.getWriter().print(schedule[j][p]);
				}
				resp.getWriter().println();
			}
			destroy();
			resp.getWriter().print(hitCount);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
