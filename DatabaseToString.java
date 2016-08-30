import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Statement;

public class DatabaseToString {
	private static Statement st;

	/** Printing the number of sections per course */
	public static void numSectionsToString() throws Exception {
		Connection conn = GetConnection.getConnection();
		st = conn.createStatement();
		ArrayList<String> num_of_sec = new ArrayList<String>();
		String sql = ("SELECT * From courses;");
		ResultSet coursesrs = st.executeQuery(sql);
		while (coursesrs.next()) {
			num_of_sec.add(
					coursesrs.getString("Course_Name") + ", Num Sections = " + coursesrs.getInt("Num_Of_Sections"));
		}
		for (int p = 0; p < num_of_sec.size(); p++) {
			System.out.println(num_of_sec.get(p));
		}
	}

	/** Printing the start time and end time of the day for each section */
	public static void startAndEndTimesToString() throws Exception {
		Connection conn = GetConnection.getConnection();
		st = conn.createStatement();
		String sql = ("SELECT * From monday_time;");
		ResultSet mondayrs = st.executeQuery(sql);
		while (mondayrs.next()) {
			System.out.println(
					mondayrs.getString("Course_Name") + " - " + mondayrs.getString("Section_Number") + " Starts day at:"
							+ mondayrs.getInt("Start_Time") + ", Ends day at: " + mondayrs.getInt("End_Time"));

		}
	}

	/* Printing Monday - Friday for each section of all courses */
	public static void databaseToString() throws Exception {
		Connection conn = GetConnection.getConnection();
		st = conn.createStatement();
		// timelist2: Used as a reference to print the database
		ArrayList<String> timelist2 = new ArrayList<String>();
		try {
			File file = new File("C:/Users/Mary/Desktop/WeberKurniawanGilder_project/timelist" + ".csv");
			FileReader inputFile = new FileReader(file);
			BufferedReader in = new BufferedReader(inputFile);
			String next = "";
			while (in.ready()) {
				next = in.readLine();
				timelist2.add("Time_" + next);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> mondaysections = new ArrayList<String>();
		String sql = ("SELECT * From monday_time;");
		ResultSet mondayrs = st.executeQuery(sql);
		ArrayList<Integer> mondaytimes = new ArrayList<Integer>();
		while (mondayrs.next()) {
			mondaysections.add(mondayrs.getString("Course_Name") + " " + mondayrs.getString("Section_Number"));
			for (int t = 0; t < timelist2.size(); t++) {
				mondaytimes.add(mondayrs.getInt(timelist2.get(t)));
			}
		}
		ArrayList<String> tuesdaysections = new ArrayList<String>();
		sql = ("SELECT * From tuesday_time;");
		ResultSet tuesdayrs = st.executeQuery(sql);
		ArrayList<Integer> tuesdaytimes = new ArrayList<Integer>();
		while (tuesdayrs.next()) {
			tuesdaysections.add(tuesdayrs.getString("Course_Name") + " " + tuesdayrs.getString("Section_Number"));
			for (int t = 0; t < timelist2.size(); t++) {
				tuesdaytimes.add(tuesdayrs.getInt(timelist2.get(t)));
			}
		}
		ArrayList<String> wednesdaysections = new ArrayList<String>();
		sql = ("SELECT * From wednesday_time;");
		ResultSet wednesdayrs = st.executeQuery(sql);
		ArrayList<Integer> wednesdaytimes = new ArrayList<Integer>();
		while (wednesdayrs.next()) {
			wednesdaysections.add(wednesdayrs.getString("Course_Name") + " " + wednesdayrs.getString("Section_Number"));
			for (int t = 0; t < timelist2.size(); t++) {
				wednesdaytimes.add(wednesdayrs.getInt(timelist2.get(t)));
			}
		}
		ArrayList<String> thursdaysections = new ArrayList<String>();
		sql = ("SELECT * From thursday_time;");
		ResultSet thursdayrs = st.executeQuery(sql);
		ArrayList<Integer> thursdaytimes = new ArrayList<Integer>();
		while (thursdayrs.next()) {
			thursdaysections.add(thursdayrs.getString("Course_Name") + " " + thursdayrs.getString("Section_Number"));
			for (int t = 0; t < timelist2.size(); t++) {
				thursdaytimes.add(thursdayrs.getInt(timelist2.get(t)));
			}
		}
		ArrayList<String> fridaysections = new ArrayList<String>();
		sql = ("SELECT * From friday_time;");
		ResultSet fridayrs = st.executeQuery(sql);
		ArrayList<Integer> fridaytimes = new ArrayList<Integer>();
		while (fridayrs.next()) {
			fridaysections.add(fridayrs.getString("Course_Name") + " " + fridayrs.getString("Section_Number"));
			for (int t = 0; t < timelist2.size(); t++) {
				fridaytimes.add(fridayrs.getInt(timelist2.get(t)));
			}
		}

		for (int h = 0; h < mondaysections.size(); h++) {
			System.out.println(mondaysections.get(h));
			for (int t = 0; t < timelist2.size(); t++) {
				System.out.print(mondaytimes.get(0));
				mondaytimes.remove(0);
			}
			System.out.println();
			for (int t = 0; t < timelist2.size(); t++) {
				System.out.print(tuesdaytimes.get(0));
				tuesdaytimes.remove(0);
			}
			System.out.println();
			for (int t = 0; t < timelist2.size(); t++) {
				System.out.print(wednesdaytimes.get(0));
				wednesdaytimes.remove(0);
			}
			System.out.println();
			for (int t = 0; t < timelist2.size(); t++) {
				System.out.print(thursdaytimes.get(0));
				thursdaytimes.remove(0);
			}
			System.out.println();
			for (int t = 0; t < timelist2.size(); t++) {
				System.out.print(fridaytimes.get(0));
				fridaytimes.remove(0);
			}
			System.out.println();
		}
	}
}
