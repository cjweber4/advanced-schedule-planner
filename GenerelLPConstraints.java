
///////////////////////////////////////////////////////////////////////////////
// Main Class:       AdvancedSchedulePlanner
// File:             GenerelLPConstraints
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Statement;
import scpsolver.constraints.LinearEqualsConstraint;
import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.problems.LinearProgram;

//
public class GenerelLPConstraints {

	/** Initializes Statement to use throughout class */
	public static Statement st;
	/**
	 * The variables below hold the total amount of sections for each courses
	 * summed for each day
	 */
	public static int mondayTotalSections = 0;
	public static int tuesdayTotalSections = 0;
	public static int wednesdayTotalSections = 0;
	public static int thursdayTotalSections = 0;
	public static int fridayTotalSections = 0;
	public static int totalNumSections = 0;
	public static double[] timesComp = {};

	/**
	 * Variable p holds the number of general linear problem constraints that
	 * have been added to this linear optimization problem
	 */
	public static int p;

	/**
	 * The array lists below hold the actual start times for each section on
	 * each day. The order in the list matters. The first course parameter has
	 * it's sections iterated through and the times are inserted into the
	 * arrays. Thus the length of all arrays will be equal and corresponding
	 * indices of the arrays are the same section. A start time of zero means no
	 * class that day for the sections
	 */
	public static ArrayList<Integer> mondayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> tuesdayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> wednesdayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> thursdayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> fridayStartTimes = new ArrayList<Integer>();
	public static ArrayList<String> sections = new ArrayList<String>();

	/**
	 * generalLPConstraints constructs the linear program with the general
	 * constraints
	 * 
	 * @return LinearProgram to be solved
	 */
	public static LinearProgram generalLPConstraints(String[] courses) throws Exception {
		Connection conn = GetConnection.getConnection();
		st = conn.createStatement();

		// Creating a string to be used for querying the coursesDB database to
		// retrieve course data in the exact same order for each day
		String courseNames = "";
		for (int numCourses = 0; numCourses < courses.length; numCourses++) {
			courseNames = courseNames + "Course_Name = '" + courses[numCourses] + "' ";
			if (numCourses != courses.length - 1) {
				courseNames = courseNames + "OR ";
			}
		}

		// timelist2: Holds the possible times of the day that class is held, in
		// five minute intervals. Used as a reference to print the database
		ArrayList<String> timelist2 = new ArrayList<String>();
		try {
			File file = new File(
					"C:/Users/Mary/Desktop/College Work/Advanced Schedule Planner/WeberKurniawanGilder_project/timelist"
							+ ".csv");
			FileReader inputFile = new FileReader(file);
			BufferedReader in = new BufferedReader(inputFile);
			String next = "";
			// Importing the times into the timelist2 arraylist.
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

		//////////////// Importing Monday Data//////////////////////
		// ArrayList that holds the number of sections each course has on Monday
		ArrayList<Integer> mondayCourseSections = new ArrayList<Integer>();
		// An arrayList that holds the corresponding course name and section
		// number to each entry into mondayStartTimes
		ArrayList<String> mondaysections = new ArrayList<String>();
		String sql = ("SELECT Course_Name, Section_Number, Start_Time From monday_time Where " + courseNames + ";");
		ResultSet mondayrsl = st.executeQuery(sql);
		while (mondayrsl.next()) {
			mondaysections.add(mondayrsl.getString("Course_Name") + " " + mondayrsl.getString("Section_Number"));
			mondayStartTimes.add(mondayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses WHERE " + courseNames + ";");
		ResultSet sectionNumrs = st.executeQuery(sql);

		// mondayCourseSections is used by programmer to make sure all sections
		// are accounted for.
		while (sectionNumrs.next()) {
			mondayCourseSections.add(sectionNumrs.getInt("Num_Of_Sections"));
		}
		mondayTotalSections = mondayStartTimes.size();

		//////////////// Importing Tuesday Data//////////////////////
		// ArrayList that holds the number of sections each course has on
		//////////////// Tuesday
		ArrayList<Integer> tuesdayCourseSections = new ArrayList<Integer>();
		// An arrayList that holds the corresponding course name and section
		// number to each entry into tuesdayStartTimes
		ArrayList<String> tuesdaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From tuesday_time Where " + courseNames + ";");
		ResultSet tuesdayrsl = st.executeQuery(sql);
		while (tuesdayrsl.next()) {
			tuesdaysections.add(tuesdayrsl.getString("Course_Name") + " " + tuesdayrsl.getString("Section_Number"));
			// System.out.print(tuesdayrsl.getString("Course_Name") + " " +
			// tuesdayrsl.getString("Section_Number"));
			tuesdayStartTimes.add(tuesdayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses WHERE " + courseNames + ";");
		ResultSet sectionNumrs2 = st.executeQuery(sql);
		// tuesdayCourseSections is used by programmer to make sure all sections
		// are accounted for.
		while (sectionNumrs2.next()) {
			tuesdayCourseSections.add(sectionNumrs2.getInt("Num_Of_Sections"));
		}
		tuesdayTotalSections = tuesdayStartTimes.size();

		//////////////// Importing Wednesday Data//////////////////////
		// ArrayList that holds the number of sections each course has on
		//////////////// Wednesday
		ArrayList<Integer> wednesdayCourseSections = new ArrayList<Integer>();
		// An arrayList that holds the corresponding course name and section
		// number to each entry into wednesdayStartTimes
		ArrayList<String> wednesdaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From wednesday_time Where " + courseNames + ";");
		ResultSet wednesdayrsl = st.executeQuery(sql);
		while (wednesdayrsl.next()) {
			wednesdaysections
					.add(wednesdayrsl.getString("Course_Name") + " " + wednesdayrsl.getString("Section_Number"));
			wednesdayStartTimes.add(wednesdayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses WHERE " + courseNames + ";");
		ResultSet sectionNumrs3 = st.executeQuery(sql);
		// wednesdayCourseSections is used by programmer to make sure all
		// sections are accounted for.
		while (sectionNumrs3.next()) {
			wednesdayCourseSections.add(sectionNumrs3.getInt("Num_Of_Sections"));
		}
		wednesdayTotalSections = wednesdayStartTimes.size();

		//////////////// Importing Thursday Data//////////////////////
		// ArrayList that holds the number of sections each course has on
		//////////////// Thursday
		ArrayList<Integer> thursdayCourseSections = new ArrayList<Integer>();
		ArrayList<String> thursdaysections = new ArrayList<String>();
		// An arrayList that holds the corresponding course name and section
		// number to each entry into thursdayStartTimes
		sql = ("SELECT Course_Name, Section_Number, Start_Time From thursday_time Where " + courseNames + ";");
		ResultSet thursdayrsl = st.executeQuery(sql);
		while (thursdayrsl.next()) {
			thursdaysections.add(thursdayrsl.getString("Course_Name") + " " + thursdayrsl.getString("Section_Number"));
			thursdayStartTimes.add(thursdayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses WHERE " + courseNames + ";");
		ResultSet sectionNumrs4 = st.executeQuery(sql);
		// thursdayCourseSections is used by programmer to make sure all
		// sections are accounted for.
		while (sectionNumrs4.next()) {
			thursdayCourseSections.add(sectionNumrs4.getInt("Num_Of_Sections"));
		}
		thursdayTotalSections = thursdayStartTimes.size();

		//////////////// Importing Friday Data//////////////////////
		// ArrayList that holds the number of sections each course has on
		//////////////// Friday
		ArrayList<Integer> fridayCourseSections = new ArrayList<Integer>();
		// An arrayList that holds the corresponding course name and section
		// number to each entry into fridayStartTimes
		ArrayList<String> fridaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From friday_time Where " + courseNames + ";");
		ResultSet fridayrsl = st.executeQuery(sql);
		while (fridayrsl.next()) {
			fridaysections.add(fridayrsl.getString("Course_Name") + " " + fridayrsl.getString("Section_Number"));
			fridayStartTimes.add(fridayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses WHERE " + courseNames + ";");
		ResultSet sectionNumrs5 = st.executeQuery(sql);
		fridayCourseSections = new ArrayList<Integer>();
		// fridayCourseSections is used by programmer to make sure all
		// sections are accounted for.
		while (sectionNumrs5.next()) {
			fridayCourseSections.add(sectionNumrs5.getInt("Num_Of_Sections"));
		}
		fridayTotalSections = fridayStartTimes.size();

		// Creating an arraylist of the sections to reference when the solution
		// is found
		sql = ("SELECT Course_Name, Section_Number From sections Where " + courseNames + ";");
		ResultSet mondayrs = st.executeQuery(sql);
		while (mondayrs.next()) {
			sections.add(mondayrs.getString("Course_Name") + " " + mondayrs.getString("Section_Number"));
		}

		// Calculating the total number classes per week (each time that every
		// section meets)
		totalNumSections = mondayTotalSections + tuesdayTotalSections + thursdayTotalSections + fridayTotalSections
				+ wednesdayTotalSections;

		// constraints holds the constraints used to create the linear program.
		ArrayList<double[]> constraints = new ArrayList<double[]>();
		// Times holds all the start times of the classes throughout the week.
		// It holds (number of sections)*5 times. This is because we need to
		// hold what time each section start each day. Five extra indices are
		// added which will hold the earliest time of each day that a certain
		// schedule starts. These are going to be maximized to give the latest
		// start time each day.
		double[] times = new double[totalNumSections + 5];
		int timesCount = 0;
		for (int j = 0; j < mondayTotalSections; j++) {
			times[timesCount] = mondayStartTimes.get(j);
			timesCount++;
		}
		for (int j = 0; j < tuesdayTotalSections; j++) {
			times[timesCount] = tuesdayStartTimes.get(j);
			timesCount++;
		}
		for (int j = 0; j < wednesdayTotalSections; j++) {
			times[timesCount] = wednesdayStartTimes.get(j);
			timesCount++;
		}
		for (int j = 0; j < thursdayTotalSections; j++) {
			times[timesCount] = thursdayStartTimes.get(j);
			timesCount++;
		}
		for (int j = 0; j < fridayTotalSections; j++) {
			times[timesCount] = fridayStartTimes.get(j);
			timesCount++;
		}

		// Compensating for having no class on a day (Want that picked, thus
		// time for start of day should be greater for the latest start time,
		// therefore that time will be set to 9999 so it is chosen. (Not
		// effective)
		timesComp = times;
		for (int i = 0; i < times.length - 5; i++) {
			if (times[i] == 0) {
				timesComp[i] = 00;
			}
		}

		/// SETTING UP CONSTRAINT THAT EACH COURSE NEEDS JUST ONE SECTION
		int totalSections = 0;
		// Looking at each course individually
		for (int i = 0; i < courses.length; i++) {
			double[] constraintHold = new double[mondayStartTimes.size() + 5];
			if (totalSections == 0) {
				// Setting the first course's sections equal to 1. The variables
				// in the linear problem will be binary and another constraint
				// will be added that a variable times each of these will need
				// to be one. Then the sum of these can only be one, thus only
				// one section is selected. Note, the program will have as many
				// variables as there are sections, with each variable being
				// either 1 or 0.
				for (int j = totalSections; j < mondayCourseSections.get(i); j++) {
					constraintHold[j] = 1;
					totalSections++;
				}
			} else {
				// Setting the following courses sections all to 1.
				if (totalSections < times.length - 5) {
					for (int j = totalSections; j < totalSections + mondayCourseSections.get(i); j++) {
						constraintHold[j] = 1;
					}
					totalSections = totalSections + mondayCourseSections.get(i);
				}
			}
			// Setting the rest of the sections that are not associated with
			// this course to zero.
			for (int j = totalSections; j < mondayStartTimes.size() + 5; j++) {
				constraintHold[j] = 0;
			}
			// Adding the constraint set up for the course to the constraints
			// arraylist
			constraints.add(constraintHold);
		}

		///////////////// STARTING LINEAR PROGRAM //////////////////
		// Adding 5 variables for start times each day, making them
		// very large to make them the only variables concerned with
		// during the maximization (Not effective)

		double[] variables = new double[times.length];
		for (int i = 0; i < times.length - 5; i++) {
			variables[i] = timesComp[i];
		}
		// Assigning 1 to the variables holding the earliest start time for each
		// day, because these will be maximized to start the latest and will
		// never be less than one
		for (int i = times.length - 5; i < variables.length; i++) {
			variables[i] = 1;
		}

		// Section Constraint: Each sections variable from each day needs to be
		// equal in order to ensure just one section from each course is
		// selected.
		// All variable holds section constraints
		ArrayList<double[]> all = new ArrayList<double[]>();

		// Only need to iterate through thursday, because Thursday is associated
		// with Friday in code below
		for (int i = 0; i < totalNumSections - mondayTotalSections; i++) {
			double[] sectionConstraint = new double[totalNumSections + 5];
			// Initializing all to zero
			for (int j = 0; j < totalNumSections; j++) {
				sectionConstraint[i] = 0;
			}
			// Saying that if a section is selected on one day, the next days
			// section must also be selected. This is done by setting the next
			// day equal to -1. Thus when the constraint is made in the program,
			// the section variables*constraint will need to add up to zero, so
			// the section variables will be one.
			sectionConstraint[i] = 1;
			sectionConstraint[i + mondayTotalSections] = -1;
			all.add(sectionConstraint);
		}

		// Time constraint: No overlapping time during the
		// day//////////////////////////////////////////////////////////////
		int numSections = 0;
		// Arraylist to hold the values (0's or 1's) for each timeslot
		// throughout the day for every day of the week. All of these values
		// added together need to be equal to 1 or zero. It can not be greater
		// than one, because then more than one section will have class during
		// that time.
		ArrayList<double[]> timeSlots = new ArrayList<double[]>();
		sql = ("SELECT Num_Of_Sections From courses Where " + courseNames + ";");
		ResultSet courseInfo = st.executeQuery(sql);
		courseInfo.next();
		numSections = numSections + courseInfo.getInt("Num_Of_Sections");
		//
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM monday_time WHERE " + courseNames
					+ ";");
			mondayrs = st.executeQuery(sql);
			// Array to hold the timeslots info for the constraint building
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			// Copying timeslot info into array
			while (mondayrs.next()) {
				timeSlotInfo[hold] = mondayrs.getInt(timelist2.get(n));
				hold++;
			}
			// Setting the values not associated with current day (Monday in
			// this case) to zero.
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			timeSlots.add(timeSlotInfo);
		}
		// Repeating process for Tuesday (See Monday)
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM tuesday_time WHERE " + courseNames
					+ ";");
			ResultSet tuesdayrs = st.executeQuery(sql);
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			while (hold < mondayTotalSections) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			while (tuesdayrs.next()) {
				timeSlotInfo[hold] = tuesdayrs.getInt(timelist2.get(n));
				hold++;
			}
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			timeSlots.add(timeSlotInfo);
		}
		// Repeating process for Wednesday (See Monday)
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM wednesday_time WHERE "
					+ courseNames + ";");
			ResultSet wednesdayrs = st.executeQuery(sql);
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			while (hold < mondayTotalSections + tuesdayTotalSections) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			while (wednesdayrs.next()) {
				timeSlotInfo[hold] = wednesdayrs.getInt(timelist2.get(n));
				hold++;
			}
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			timeSlots.add(timeSlotInfo);
		}
		// Repeating process for Thursday (See Monday)
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM thursday_time WHERE "
					+ courseNames + ";");
			ResultSet thursdayrs = st.executeQuery(sql);
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			while (hold < mondayTotalSections + tuesdayTotalSections + wednesdayTotalSections) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			while (thursdayrs.next()) {
				timeSlotInfo[hold] = thursdayrs.getInt(timelist2.get(n));
				hold++;
			}
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			timeSlots.add(timeSlotInfo);
		}
		// Repeating process for Friday (See Monday)
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM friday_time WHERE " + courseNames
					+ ";");
			ResultSet fridayrs = st.executeQuery(sql);
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			while (hold < mondayTotalSections + tuesdayTotalSections + wednesdayTotalSections + thursdayTotalSections) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			while (fridayrs.next()) {
				timeSlotInfo[hold] = fridayrs.getInt(timelist2.get(n));
				hold++;
			}
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			timeSlots.add(timeSlotInfo);
		}

		/////////////////////////////////////////////////////

		// CREATING LINEAR PROGRAM - has same number of variables as number of
		// sections + 5
		LinearProgram lp = new LinearProgram(variables);
		for (int i = 0; i < times.length; i++) {
			if (i < times.length - 5) {
				// Setting sections variables to binary
				lp.setBinary(i);
			} else {
				// Setting day of week start times to integers
				lp.setInteger(i);
			}
		}

		////// Courses constraint: Each course needs just 1 section /////////
		p = 1;
		String constraintName = "";
		for (int i = 0; i < constraints.size(); i++) {
			constraintName = "c" + Integer.toString(p);
			// Each array in constraint has every index multiplied by the
			// corresponding variable in the linear program. The sum of these is
			// set to 1, as indicated below.
			lp.addConstraint(new LinearEqualsConstraint(constraints.get(i), 1.0, constraintName));
			p++;
		}
		constraintName = "";
		for (int i = 0; i < all.size(); i++) {
			constraintName = "c" + Integer.toString(p);
			// Each array in all has every index multiplied by the
			// corresponding variable in the linear program. The sum of these is
			// set to 0, as indicated below.
			lp.addConstraint(new LinearEqualsConstraint(all.get(i), 0.0, constraintName));
			p++;
		}

		/////////// Time Slots constraint: Each time slot can only have one
		/////////// class ////////////////////////////////////////////////
		for (int i = 0; i < timeSlots.size(); i++) {
			constraintName = "c" + Integer.toString(p);
			// Each array in timeslots has every index multiplied by the
			// corresponding variable in the linear program. The sum of these is
			// set to 1, as indicated below.
			lp.addConstraint(new LinearSmallerThanEqualsConstraint(timeSlots.get(i), 1.0, constraintName));
			p++;
		}
		st.close();
		conn.close();
		return lp;
	}

}
