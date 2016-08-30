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

public class GenerelLPConstraints {

	public static Statement st;
	public static int mondayTotalSections = 0;
	public static int tuesdayTotalSections = 0;
	public static int wednesdayTotalSections = 0;
	public static int thursdayTotalSections = 0;
	public static int fridayTotalSections = 0;
	public static int totalNumSections = 0;
	public static double[] timesComp = {};
	public static int p;
	public static ArrayList<Integer> mondayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> tuesdayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> wednesdayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> thursdayStartTimes = new ArrayList<Integer>();
	public static ArrayList<Integer> fridayStartTimes = new ArrayList<Integer>();
	public static ArrayList<String> sections = new ArrayList<String>();

	public static LinearProgram generalLPConstraints(String[] courses) throws Exception {
		Connection conn = GetConnection.getConnection();
		st = conn.createStatement();
		String courseNames = "";
		for (int numCourses = 0; numCourses < courses.length; numCourses++) {
			courseNames = courseNames + "Course_Name = '" + courses[numCourses] + "' ";
			if (numCourses != courses.length - 1) {
				courseNames = courseNames + "OR ";
			}
		}
		System.out.println();

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

		//////////////// Importing Monday Data//////////////////////
		ArrayList<Integer> mondayCourseSections = new ArrayList<Integer>();

		double[] mondayTimes = new double[mondayStartTimes.size()];
		ArrayList<String> mondaysections = new ArrayList<String>();
		String sql = ("SELECT Course_Name, Section_Number, Start_Time From monday_time Where " + courseNames + ";");
		ResultSet mondayrsl = st.executeQuery(sql);
		while (mondayrsl.next()) {
			mondaysections.add(mondayrsl.getString("Course_Name") + " " + mondayrsl.getString("Section_Number"));
			//System.out.print(mondayrsl.getString("Course_Name") + " " + mondayrsl.getString("Section_Number"));
			mondayStartTimes.add(mondayrsl.getInt("Start_Time"));
		}
		
		sql = ("SELECT Course_Name, Num_Of_Sections From courses WHERE " + courseNames + ";");
		ResultSet sectionNumrs = st.executeQuery(sql);
		mondayCourseSections = new ArrayList<Integer>();
		while (sectionNumrs.next()) {
			mondayCourseSections.add(sectionNumrs.getInt("Num_Of_Sections"));

		}
		mondayTimes = new double[mondayStartTimes.size()];
		for (int i = 0; i < mondayStartTimes.size(); i++) {
			mondayTimes[i] = mondayStartTimes.get(i);
		}
		mondayTotalSections = mondayTimes.length;

		//////////////// Importing Tuesday Data//////////////////////
		ArrayList<Integer> tuesdayCourseSections = new ArrayList<Integer>();
		double[] tuesdayTimes = new double[tuesdayStartTimes.size()];
		ArrayList<String> tuesdaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From tuesday_time Where " + courseNames + ";");
		ResultSet tuesdayrsl = st.executeQuery(sql);
		while (tuesdayrsl.next()) {
			tuesdaysections.add(tuesdayrsl.getString("Course_Name") + " " + tuesdayrsl.getString("Section_Number"));
			//System.out.print(tuesdayrsl.getString("Course_Name") + " " + tuesdayrsl.getString("Section_Number"));
			tuesdayStartTimes.add(tuesdayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses;");
		sectionNumrs = st.executeQuery(sql);
		tuesdayCourseSections = new ArrayList<Integer>();
		while (sectionNumrs.next()) {
			tuesdayCourseSections.add(sectionNumrs.getInt("Num_Of_Sections"));
		}
		tuesdayTimes = new double[tuesdayStartTimes.size()];
		for (int i = 0; i < tuesdayStartTimes.size(); i++) {
			tuesdayTimes[i] = tuesdayStartTimes.get(i);
		}
		tuesdayTotalSections = tuesdayTimes.length;

		//////////////// Importing Wednesday Data//////////////////////
		ArrayList<Integer> wednesdayCourseSections = new ArrayList<Integer>();
		double[] wednesdayTimes = new double[wednesdayStartTimes.size()];
		ArrayList<String> wednesdaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From wednesday_time Where " + courseNames + ";");
		ResultSet wednesdayrsl = st.executeQuery(sql);
		while (wednesdayrsl.next()) {
			wednesdaysections
					.add(wednesdayrsl.getString("Course_Name") + " " + wednesdayrsl.getString("Section_Number"));
			//System.out.print(wednesdayrsl.getString("Course_Name") + " " + wednesdayrsl.getString("Section_Number"));
			wednesdayStartTimes.add(wednesdayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses;");
		sectionNumrs = st.executeQuery(sql);
		wednesdayCourseSections = new ArrayList<Integer>();
		while (sectionNumrs.next()) {
			wednesdayCourseSections.add(sectionNumrs.getInt("Num_Of_Sections"));
		}
		wednesdayTimes = new double[wednesdayStartTimes.size()];
		for (int i = 0; i < wednesdayStartTimes.size(); i++) {
			wednesdayTimes[i] = wednesdayStartTimes.get(i);
		}
		wednesdayTotalSections = wednesdayTimes.length;

		//////////////// Importing Thursday Data//////////////////////
		ArrayList<Integer> thursdayCourseSections = new ArrayList<Integer>();
		double[] thursdayTimes = new double[thursdayStartTimes.size()];
		ArrayList<String> thursdaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From thursday_time Where " + courseNames + ";");
		ResultSet thursdayrsl = st.executeQuery(sql);
		while (thursdayrsl.next()) {
			thursdaysections.add(thursdayrsl.getString("Course_Name") + " " + thursdayrsl.getString("Section_Number"));
			//System.out.print(thursdayrsl.getString("Course_Name") + " " + thursdayrsl.getString("Section_Number"));
			thursdayStartTimes.add(thursdayrsl.getInt("Start_Time"));
		}
		sql = ("SELECT Course_Name, Num_Of_Sections From courses;");
		sectionNumrs = st.executeQuery(sql);
		thursdayCourseSections = new ArrayList<Integer>();
		while (sectionNumrs.next()) {
			thursdayCourseSections.add(sectionNumrs.getInt("Num_Of_Sections"));
		}
		thursdayTimes = new double[thursdayStartTimes.size()];
		for (int i = 0; i < thursdayStartTimes.size(); i++) {
			thursdayTimes[i] = thursdayStartTimes.get(i);
		}
		thursdayTotalSections = thursdayTimes.length;

		//////////////// Importing Friday Data//////////////////////
		ArrayList<Integer> fridayCourseSections = new ArrayList<Integer>();
		double[] fridayTimes = new double[fridayStartTimes.size()];
		ArrayList<String> fridaysections = new ArrayList<String>();
		sql = ("SELECT Course_Name, Section_Number, Start_Time From friday_time Where " + courseNames + ";");
		ResultSet fridayrsl = st.executeQuery(sql);
		while (fridayrsl.next()) {
			fridaysections.add(fridayrsl.getString("Course_Name") + " " + fridayrsl.getString("Section_Number"));
			//System.out.print(fridayrsl.getString("Course_Name") + " " + fridayrsl.getString("Section_Number"));
			fridayStartTimes.add(fridayrsl.getInt("Start_Time"));
		}
		//System.out.println();

		sql = ("SELECT Course_Name, Num_Of_Sections From courses;");
		sectionNumrs = st.executeQuery(sql);
		fridayCourseSections = new ArrayList<Integer>();
		while (sectionNumrs.next()) {
			fridayCourseSections.add(sectionNumrs.getInt("Num_Of_Sections"));
		}
		fridayTimes = new double[fridayStartTimes.size()];
		for (int i = 0; i < fridayStartTimes.size(); i++) {
			fridayTimes[i] = fridayStartTimes.get(i);
		}
		fridayTotalSections = fridayTimes.length;

		// Creating an arraylist to reference when the solution is found
		sql = ("SELECT Course_Name, Section_Number From sections Where " + courseNames + ";");
		ResultSet mondayrs = st.executeQuery(sql);
		while (mondayrs.next()) {
			sections.add(mondayrs.getString("Course_Name") + " " + mondayrs.getString("Section_Number"));
		}

		// System.out.println("Here");

		totalNumSections = mondayTotalSections + tuesdayTotalSections + thursdayTotalSections + fridayTotalSections
				+ wednesdayTotalSections;
		ArrayList<double[]> constraints = new ArrayList<double[]>();
		double[] times = new double[totalNumSections + 5];
		int timesCount = 0;
		for (int j = 0; j < mondayTotalSections; j++) {
			// System.out.println(mondayTimes[j]);

			times[timesCount] = mondayTimes[j];
			timesCount++;
		}
		for (int j = 0; j < tuesdayTotalSections; j++) {
			times[timesCount] = tuesdayTimes[j];
			timesCount++;
		}
		for (int j = 0; j < wednesdayTotalSections; j++) {
			times[timesCount] = wednesdayTimes[j];
			timesCount++;
		}
		for (int j = 0; j < thursdayTotalSections; j++) {
			times[timesCount] = thursdayTimes[j];
			timesCount++;
		}
		for (int j = 0; j < fridayTotalSections; j++) {
			times[timesCount] = fridayTimes[j];
			timesCount++;
		}
		// Compensating for having no class on a day (Want that picked, thus
		// time for start of day should be greater for the latest start time,
		// therefore that time will be set to 9999 so it is chosen. (Not
		// effective)
		//System.out.println("hereppppppppppppppppppppp" + totalNumSections);
		timesComp = times;
		for (int i = 0; i < times.length - 5; i++) {
			if (times[i] == 0) {
				timesComp[i] = 00;
			}
			// System.out.print(timesComp[i]);

		}
		for (int i = 0; i < timesComp.length; i++) {
			//System.out.print(timesComp[i]);
		}
		// System.out.println("Here");
		int totalSections = 0;
		for (int i = 0; i < courses.length; i++) {
			double[] constraintHold = new double[mondayStartTimes.size() + 5];
			// System.out.println(mondayStartTimes.size());
			// System.out.println(courseSections.get(i));
			if (totalSections == 0) {
				//System.out.println(mondayCourseSections.get(i));
				for (int j = totalSections; j < mondayCourseSections.get(i); j++) {
					constraintHold[j] = 1;
					totalSections++;
					// System.out.println("Here1" + i);
				}
			} else {
				// System.out.println("here");

				// System.out.println("total sections = " + totalSections);
				if (totalSections < times.length - 5) {

					// System.out.println(mondayCourseSections.get(i));
					for (int j = totalSections; j < totalSections + mondayCourseSections.get(i); j++) {
						// System.out.println("here");
						constraintHold[j] = 1;
					}
					totalSections = totalSections + mondayCourseSections.get(i);

				}
			}
			for (int j = totalSections; j < mondayStartTimes.size() + 5; j++) {
				constraintHold[j] = 0;

			}
			for (int k = 0; k < constraintHold.length; k++) {
				//System.out.print(constraintHold[k]);
			}
			//System.out.println();
			constraints.add(constraintHold);
		}

		for (int i = 0; i < constraints.get(0).length - 5; i++) {
			// double[] k = constraints.get(2);
			// System.out.print(k[i] + " ");
		}
		///////////////// STARTING LINEAR PROGRAM //////////////////
		// Adding 5 variables for start times each day, making them
		// very large to make them the only variables concerned with
		// during the maximization
		//System.out.println();
		//System.out.println("here");
		double[] variables = new double[times.length];
		for (int i = 0; i < times.length - 5; i++) {
			variables[i] = timesComp[i];
			//System.out.print(variables[i]);
		}
		for (int i = times.length - 5; i < variables.length; i++) {
			variables[i] = 1;
			//System.out.print(variables[i]);
		}
		//System.out.println(times.length);

		String timeSQLMonday = "";
		// System.out.println(timelist2.size());
		for (int i = 0; i < timelist2.size(); i++) {
			timeSQLMonday = timeSQLMonday + timelist2.get(i) + ", ";
		}
		// System.out.println(timeSQLMonday);

		// Section Constraint: Each sections variable from each day needs to be
		// equal
		ArrayList<double[]> all = new ArrayList<double[]>();
		for (int i = 0; i < totalNumSections - mondayTotalSections; i++) {
			double[] sectionConstraint = new double[totalNumSections + 5];
			for (int j = 0; j < totalNumSections; j++) {
				sectionConstraint[i] = 0;
			}
			sectionConstraint[i] = 1;
			sectionConstraint[i + mondayTotalSections] = -1;
			all.add(sectionConstraint);
		}
		for (int i = 0; i < all.size(); i++) {
			double[] hold = all.get(i);
			for (int j = 0; j < hold.length; j++) {
				// System.out.print(hold[j]);
			}
			// System.out.println();
		}

		// System.out.println(j);

		// Time constraint: No overlapping time during the
		// day//////////////////////////////////////////////////////////////
		int numSections = 0;
		ArrayList<double[]> timeSlots = new ArrayList<double[]>();
		// System.out.println(timelist2.get(0));
		sql = ("SELECT Num_Of_Sections From courses Where " + courseNames + ";");
		ResultSet courseInfo = st.executeQuery(sql);
		courseInfo.next();
		numSections = numSections + courseInfo.getInt("Num_Of_Sections");

		// System.out.println("Sections = " + numSections);

		// System.out.println(courseNames);
		// String courseOrder = "";
		// ArrayList<String> order = new ArrayList<String>();
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM monday_time WHERE " + courseNames
					+ ";");
			mondayrs = st.executeQuery(sql);
			// System.out.println(mondayrs.getInt(timelist2.get(0)));
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			while (mondayrs.next()) {
				timeSlotInfo[hold] = mondayrs.getInt(timelist2.get(n));
				// System.out.print(mondayrs.getString("Course_Name") + " " +
				// mondayrs.getString("Section_Number")+". ");
				// System.out.print(timeSlotInfo[hold]);
				hold++;
				// System.out.print(hold + ".");
			}
			// System.out.println(timelist2.get(n));
			// System.out.println();
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			timeSlots.add(timeSlotInfo);
		}
		// System.out.println(timeSlots.get(4).length + " length");
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM tuesday_time WHERE " + courseNames
					+ ";");
			ResultSet tuesdayrs = st.executeQuery(sql);
			// System.out.println(tuesdayrs.getInt(timelist2.get(0)));
			double[] timeSlotInfo = new double[totalNumSections + 5];
			int hold = 0;
			while (hold < mondayTotalSections) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			while (tuesdayrs.next()) {
				timeSlotInfo[hold] = tuesdayrs.getInt(timelist2.get(n));
				// System.out.print(tuesdayrs.getString("Course_Name") + " " +
				// tuesdayrs.getString("Section_Number")+". ");
				hold++;
			}
			while (hold < totalNumSections + 5) {
				timeSlotInfo[hold] = 0;
				hold++;
			}
			// System.out.println();
			timeSlots.add(timeSlotInfo);
		}
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM wednesday_time WHERE "
					+ courseNames + ";");
			ResultSet wednesdayrs = st.executeQuery(sql);
			// System.out.println(tuesdayrs.getInt(timelist2.get(0)));
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
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM thursday_time WHERE "
					+ courseNames + ";");
			ResultSet thursdayrs = st.executeQuery(sql);
			// System.out.println(tuesdayrs.getInt(timelist2.get(0)));
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
		for (int n = 0; n < timelist2.size(); n++) {
			sql = ("SELECT Course_Name, Section_Number, " + timelist2.get(n) + " FROM friday_time WHERE " + courseNames
					+ ";");
			ResultSet fridayrs = st.executeQuery(sql);
			// System.out.println(timelist2.get(n));
			// System.out.println(tuesdayrs.getInt(timelist2.get(0)));
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
		//System.out.println(timeSlots.size());
		//System.out.println(timelist2.size() * 5);
		// if (hold == totalNumSections-1){
		// System.out.println("yep");
		// }
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		LinearProgram lp = new LinearProgram(variables);
		//System.out.println();

		//System.out.println(times.length + "total sec =" + totalNumSections);
		for (int i = 0; i < times.length; i++) {
			if (i < times.length - 5) {
				lp.setBinary(i);
			} else {
				lp.setInteger(i);
			}
			// System.out.print(times[i]+".");
		}
		 //System.out.println(times.length + "g");
		////// Courses constraint: Each course needs just 1 sections /////////
		p = 1;
		String constraintName = "";
		for (int i = 0; i < constraints.size(); i++) {
			constraintName = "c" + Integer.toString(p);
			lp.addConstraint(new LinearEqualsConstraint(constraints.get(i), 1.0, constraintName));
			p++;
		}
		constraintName = "";
		for (int i = 0; i < all.size(); i++) {
			constraintName = "c" + Integer.toString(p);
			lp.addConstraint(new LinearEqualsConstraint(all.get(i), 0.0, constraintName));
			p++;
		}
		/////////// Time Slots constraint: Each time slot can only have one
		/////////// class ////////////////////////////////////////////////
		for (int i = 0; i < timeSlots.size(); i++) {
			constraintName = "c" + Integer.toString(p);
			// System.out.println(constraintName);
			lp.addConstraint(new LinearSmallerThanEqualsConstraint(timeSlots.get(i), 1.0, constraintName));
			p++;
		}
		st.close();
		conn.close();
		//System.out.println("oh baby");
		return lp;
	}

}
