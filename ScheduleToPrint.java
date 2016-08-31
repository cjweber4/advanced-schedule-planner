import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;

public class ScheduleToPrint {
	private static Statement st;
	public static Connection conn;

	public static String[][] scheduleToPrint(String[] coursesList) {

		String forSQL = "";
		for (int i = 0; i < coursesList.length; i++) {
			String[] hold = coursesList[i].split(" ");
			forSQL = forSQL + "(Course_Name = '" + hold[0] + " " + hold[1] + "' AND Section_Number = " + hold[2] + ") ";
			if (i != coursesList.length - 1) {
				forSQL = forSQL + "OR ";
			}

		}
		System.out.println(forSQL);

		ArrayList<String> timelist = new ArrayList<String>();
		try {
			File file = new File("C:/Users/Mary/Desktop/College Work/Advanced Schedule Planner/WeberKurniawanGilder_project/timelist" + ".csv");
			FileReader inputFile = new FileReader(file);
			BufferedReader in = new BufferedReader(inputFile);
			String next = "";
			while (in.ready()) {
				next = in.readLine();
				timelist.add("Time_" + next);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[][] schedule = new String[6][timelist.size() + 1];
		for (int p = 0; p < schedule[0].length; p++) {
			for (int j = 0; j < schedule.length; j++) {
				schedule[j][p] = "     .     ";
			}
		}

		System.out.println(schedule.length);
		for (int i = 1; i < schedule[0].length; i++) {
			schedule[0][i] = timelist.get(i - 1);
		}
		schedule[0][0] = "   Time   ";
		schedule[1][0] = "  Monday  ";
		schedule[2][0] = "  Tuesday ";
		schedule[3][0] = " Wednesday";
		schedule[4][0] = "  Thursday";
		schedule[5][0] = "  Friday  ";
		try {
			conn = GetConnection.getConnection();
			st = conn.createStatement();
			String sql = "USE coursesdb;";
			st.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e);
		}
		String[] days = { "monday_time", "tuesday_time", "wednesday_time", "thursday_time", "friday_time" };
		for (int i = 0; i < days.length; i++) {
			String sql = ("SELECT * From " + days[i] + " Where " + forSQL + ";");
			System.out.println(sql);
			try {
				ResultSet data = st.executeQuery(sql);

				while (data.next()) {
					for (int j = 0; j < timelist.size(); j++) {
						String course = data.getString("Course_Name");
						String section = data.getString("Section_Number");
						int timeSlot = data.getInt(timelist.get(j));
						if (timeSlot == 1) {
							schedule[i + 1][j + 1] = course + " " + section;
						}
					}
				}

			} catch (SQLException e) {
				System.out.println(e);
			}

		}
		for (int p = 0; p < schedule[0].length; p++) {
			for (int j = 0; j < schedule.length; j++) {
				for (int t = schedule[j][p].length(); t < 12; t++) {
					schedule[j][p] = schedule[j][p] + " ";
				}
				System.out.print(schedule[j][p]);
			}
			System.out.println();
		}
		try {
			conn.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return schedule;
	}
}
