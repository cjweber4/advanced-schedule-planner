
///////////////////////////////////////////////////////////////////////////////
// Main Class:       AdvancedSchedulePlanner
// File:             CreateCoursesDB
//
// Author:           Curtis Weber
// Email:            cjweber4@wisc.edu
// Date:         	 6/2016
///////////////////////////////////////////////////////////////////////////////
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;

/**
 * CreateCoursesDB accesses the MariaDB at local host 3306 and creates a new
 * database called CoursesDB. Inside that database a courses, sections,
 * mondaytime, tuesdaytime, wednesdaytime, thursdaytime, and fridaytime tables
 * are created to store the data for each course.
 */
public class CreateCoursesDB {

	/** Initializes Statement to use throughout class */
	private static Statement st;

	public static void createCoursesDB() throws Exception {
		// timelist2: Used as a reference to print the database
		ArrayList<String> timelist2 = new ArrayList<String>();

		////////////////////////////////////////////////////////////////////////////////
		/** Inputting Timelist values for days */

		// timelist: Holds a string of the fields to insert into the day tables
		// for the sql statement
		String timelist = "";

		// check: Holds the sql statement to check to make sure the data for
		// time is a 0 or 1
		String check = "";

		// timelist_int: Used as a reference to find the start time and end time
		// of sections each day
		ArrayList<Integer> timelist_int = new ArrayList<Integer>();

		try {
			Connection conn = GetConnection.getConnection();
			st = conn.createStatement();
			File file = new File(
					"C:/Users/Mary/Desktop/College Work/Advanced Schedule Planner/WeberKurniawanGilder_project/timelist"
							+ ".csv");
			FileReader inputFile = new FileReader(file);
			BufferedReader in = new BufferedReader(inputFile);

			String next = "";
			while (in.ready()) {
				next = in.readLine();
				timelist = timelist + "Time_" + next + " INTEGER NOT NULL, ";
				check = check + "CHECK( 0<=Time_" + next + "<=1),";
				timelist2.add("Time_" + next);
				timelist_int.add(Integer.parseInt(next));
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////////////////////////////
		/** Creating the Database */

		// Resetting the Database to rebuild with updated courses
		String sql = "DROP DATABASE CoursesDB";
		st.execute(sql);
		sql = ("CREATE DATABASE CoursesDB");
		st.execute(sql);
		sql = "USE CoursesDB";
		st.execute(sql);

		// Creating the Courses table
		sql = "CREATE TABLE courses (Course_Name CHAR(10) NOT NULL," + "Num_Of_Sections INTEGER, "
				+ "PRIMARY KEY(Course_Name), CHECK(Num_Of_Sections>0));";
		st.execute(sql);

		// Creating the Sections table to hold all sections of the courses
		sql = ("CREATE TABLE sections (Course_Name CHAR(10)," + "Section_Number INTEGER NOT NULL, "
				+ "PRIMARY KEY (Course_Name, Section_Number),"
				+ "FOREIGN KEY (Course_Name) REFERENCES courses(Course_Name) ON UPDATE CASCADE,"
				+ "CHECK(0<Section_Number));");
		st.execute(sql);

		// Creating the Monday time table to indicate when sections have class
		// on Mondays
		sql = ("CREATE TABLE monday_time ( Course_Name CHAR(10),"
				+ "Section_Number INTEGER, Start_Time INTEGER, End_Time INTEGER, " + timelist
				+ "PRIMARY KEY (Course_Name, Section_Number)," + check
				+ "FOREIGN KEY (Course_Name, Section_Number) REFERENCES sections(Course_Name,Section_Number) ON UPDATE CASCADE, "
				+ "CHECK(0<=Start_Time), CHECK(0<=End_Time));");
		st.execute(sql);
		// Creating the Tuesday time table to indicate when sections have class
		// on Tuesdays
		sql = ("CREATE TABLE tuesday_time ( Course_Name CHAR(10),"
				+ "Section_Number INTEGER, Start_Time INTEGER, End_Time INTEGER, " + timelist
				+ "PRIMARY KEY (Course_Name, Section_Number)," + check
				+ "FOREIGN KEY (Course_Name, Section_Number) REFERENCES sections(Course_Name,Section_Number) ON UPDATE CASCADE, "
				+ "CHECK(0<=Start_Time), CHECK(0<=End_Time));");
		st.execute(sql);
		// Creating the Wednesday time table to indicate when sections have
		// class on Wednesdays
		sql = ("CREATE TABLE wednesday_time ( Course_Name CHAR(10),"
				+ "Section_Number INTEGER, Start_Time INTEGER, End_Time INTEGER, " + timelist
				+ "PRIMARY KEY (Course_Name, Section_Number)," + check
				+ "FOREIGN KEY (Course_Name, Section_Number) REFERENCES sections(Course_Name,Section_Number) ON UPDATE CASCADE, "
				+ "CHECK(0<=Start_Time), CHECK(0<=End_Time));");
		st.execute(sql);
		// Creating the Thursday time table to indicate when sections have class
		// on Thursdays
		sql = ("CREATE TABLE thursday_time ( Course_Name CHAR(10),"
				+ "Section_Number INTEGER, Start_Time INTEGER, End_Time INTEGER, " + timelist
				+ "PRIMARY KEY (Course_Name, Section_Number)," + check
				+ "FOREIGN KEY (Course_Name, Section_Number) REFERENCES sections(Course_Name,Section_Number) ON UPDATE CASCADE, "
				+ "CHECK(0<=Start_Time), CHECK(0<=End_Time));");
		st.execute(sql);
		// Creating the Friday time table to indicate when sections have class
		// on Fridays
		sql = ("CREATE TABLE friday_time ( Course_Name CHAR(10),"
				+ "Section_Number INTEGER, Start_Time INTEGER, End_Time INTEGER, " + timelist
				+ "PRIMARY KEY (Course_Name, Section_Number)," + check
				+ "FOREIGN KEY (Course_Name, Section_Number) REFERENCES sections(Course_Name,Section_Number) ON UPDATE CASCADE, "
				+ "CHECK(0<=Start_Time), CHECK(0<=End_Time));");
		st.execute(sql);

		////////////////////////////////////////////////////////////////////////////////
		/** Import Excel Sheets with courses data */

		// files: The name of the files exactly as they are saved into the
		// locations indicated in the 'file' variable below
		String[] files = { "ANTH 104", "CHEM 103", "CHEM 104", "CHEM 343", "CS 302", "CS 435", "CS 524", "ECE 252",
				"MATH 240", "MUS 156" };
		for (int k = 0; k < files.length; k++) {
			try {
				File file = new File(
						"C:/Users/Mary/Desktop/College Work/Advanced Schedule Planner/WeberKurniawanGilder_project/Courses/"
								+ files[k] + ".csv");
				FileReader inputFile = new FileReader(file);
				BufferedReader in = new BufferedReader(inputFile);

				// Inserting course info into course table
				sql = ("INSERT INTO courses " + "VALUES ('" + files[k] + "', NULL)");
				st.execute(sql);
				// section_num: holds the current section number of the course
				// being uploaded from the course file
				int section_num = 0;
				// mondayStart: Holds the Monday start time of current section
				int mondayStart = 0;
				// tuesdayStart: Holds the Tuesday start time of current section
				int tuesdayStart = 0;
				// wednesdayStart: Holds the Wednesday start time of current
				// section
				int wednesdayStart = 0;
				// thursdayStart: Holds the Thursday start time of current
				// section
				int thursdayStart = 0;
				// fridayStart: Holds the Friday start time of current section
				int fridayStart = 0;
				// mondayEnd: Holds the Monday end time of current section
				int mondayEnd = 0;
				// tuesdayEnd: Holds the Tuesday end time of current section
				int tuesdayEnd = 0;
				// wednesdayEnd: Holds the Wednesday end time of the current
				// section
				int wednesdayEnd = 0;
				// thursdayEnd: Holds the Thursday end time of the current
				// section
				int thursdayEnd = 0;
				// fridayEnd: Holds the Friday end time of the current section
				int fridayEnd = 0;
				while (in.ready()) {
					// for section number
					String[] hold = in.readLine().split(",");
					section_num = Integer.parseInt(hold[0]);

					// Breaking up the section in the course file to the five
					// days of the week

					// Monday
					String monday_String = in.readLine();
					String[] mondaySplit = monday_String.split(",");
					ArrayList<Integer> mondayInt = new ArrayList<Integer>();
					for (int i = 0; i < mondaySplit.length; i++) {
						mondayInt.add(Integer.parseInt(mondaySplit[i]));
					}
					int startTime = -1;
					int endTime = -1;
					for (int i = 0; i < mondayInt.size(); i++) {
						if (startTime == -1) {
							if (mondayInt.get(i) == 1) {
								startTime = i;
							}
						}
						if (mondayInt.get(i) == 1) {
							endTime = i;
						}
					}
					if (startTime == -1) {
						mondayStart = 0;
						mondayEnd = 0;
					} else {
						mondayStart = timelist_int.get(startTime);
						mondayEnd = timelist_int.get(endTime);
					}

					// Tuesday
					String tuesday_String = in.readLine();
					String[] tuesdaySplit = tuesday_String.split(",");
					ArrayList<Integer> tuesdayInt = new ArrayList<Integer>();
					for (int i = 0; i < tuesdaySplit.length; i++) {
						tuesdayInt.add(Integer.parseInt(tuesdaySplit[i]));
					}
					startTime = -1;
					endTime = -1;
					for (int i = 0; i < tuesdayInt.size(); i++) {
						if (startTime == -1) {
							if (tuesdayInt.get(i) == 1) {
								startTime = i;
							}
						}
						if (tuesdayInt.get(i) == 1) {
							endTime = i;
						}
					}
					if (startTime == -1) {
						tuesdayStart = 0;
						tuesdayEnd = 0;
					} else {
						tuesdayStart = timelist_int.get(startTime);
						tuesdayEnd = timelist_int.get(endTime);
					}

					// Wednesday
					String wednesday_String = in.readLine();
					String[] wednesdaySplit = wednesday_String.split(",");
					ArrayList<Integer> wednesdayInt = new ArrayList<Integer>();
					for (int i = 0; i < wednesdaySplit.length; i++) {
						wednesdayInt.add(Integer.parseInt(wednesdaySplit[i]));
					}
					startTime = -1;
					endTime = -1;
					for (int i = 0; i < wednesdayInt.size(); i++) {
						if (startTime == -1) {
							if (wednesdayInt.get(i) == 1) {
								startTime = i;
							}
						}
						if (wednesdayInt.get(i) == 1) {
							endTime = i;
						}
					}
					if (startTime == -1) {
						wednesdayStart = 0;
						wednesdayEnd = 0;
					} else {
						wednesdayStart = timelist_int.get(startTime);
						wednesdayEnd = timelist_int.get(endTime);
					}

					// Thursday
					String thursday_String = in.readLine();
					String[] thursdaySplit = thursday_String.split(",");
					ArrayList<Integer> thursdayInt = new ArrayList<Integer>();
					for (int i = 0; i < thursdaySplit.length; i++) {
						thursdayInt.add(Integer.parseInt(thursdaySplit[i]));
					}
					startTime = -1;
					endTime = -1;
					for (int i = 0; i < thursdayInt.size(); i++) {
						if (startTime == -1) {
							if (thursdayInt.get(i) == 1) {
								startTime = i;
							}
						}
						if (thursdayInt.get(i) == 1) {
							endTime = i;
						}
					}
					if (startTime == -1) {
						thursdayStart = 0;
						thursdayEnd = 0;
					} else {
						thursdayStart = timelist_int.get(startTime);
						thursdayEnd = timelist_int.get(endTime);
					}

					// Friday
					String friday_String = in.readLine();
					String[] fridaySplit = friday_String.split(",");
					ArrayList<Integer> fridayInt = new ArrayList<Integer>();
					for (int i = 0; i < fridaySplit.length; i++) {
						fridayInt.add(Integer.parseInt(fridaySplit[i]));
					}
					startTime = -1;
					endTime = -1;
					for (int i = 0; i < fridayInt.size(); i++) {
						if (startTime == -1) {
							if (fridayInt.get(i) == 1) {
								startTime = i;
							}
						}
						if (fridayInt.get(i) == 1) {
							endTime = i;
						}
					}
					if (startTime == -1) {
						fridayStart = 0;
						fridayEnd = 0;
					} else {
						fridayStart = timelist_int.get(startTime);
						fridayEnd = timelist_int.get(endTime);
					}

					// Inserting the section data into the database
					sql = ("INSERT INTO sections " + "VALUES ('" + files[k] + "'," + section_num + ")");
					st.execute(sql);
					sql = ("INSERT INTO monday_time " + "VALUES ('" + files[k] + "'," + section_num + ", " + mondayStart
							+ ", " + mondayEnd + ", " + monday_String + ")");
					st.execute(sql);
					sql = ("INSERT INTO tuesday_time " + "VALUES ('" + files[k] + "'," + section_num + ", "
							+ tuesdayStart + ", " + tuesdayEnd + ", " + tuesday_String + ")");
					st.execute(sql);
					sql = ("INSERT INTO wednesday_time " + "VALUES ('" + files[k] + "'," + section_num + ", "
							+ wednesdayStart + ", " + wednesdayEnd + ", " + wednesday_String + ")");
					st.execute(sql);
					sql = ("INSERT INTO thursday_time " + "VALUES ('" + files[k] + "'," + section_num + ", "
							+ thursdayStart + ", " + thursdayEnd + ", " + thursday_String + ")");
					st.execute(sql);
					sql = ("INSERT INTO friday_time " + "VALUES ('" + files[k] + "'," + section_num + ", " + fridayStart
							+ ", " + fridayEnd + ", " + friday_String + ")");
					st.execute(sql);
				}

				sql = ("UPDATE courses" + " SET Num_Of_Sections =" + section_num + " WHERE Course_Name ='" + files[k]
						+ "';");
				st.execute(sql);
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
