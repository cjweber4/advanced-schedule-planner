///////////////////////////////////////////////////////////////////////////////
// Title:            AdvancedSchedulePlanner
// Files:            AdvancedSchedulePlanner, CreateCoursesDB, DatabaseToString, FindLatestSchedule, GeneralLPConstraints, GetConnection, Importer, NewSolve, ScheduleToPrint 
//                   Room, TheGame
//
// Author:           Curtis Weber
// Email:            cjweber4@wisc.edu
// Date:         	 6/2016
///////////////////////////////////////////////////////////////////////////////

/**
 * The AdvancedSchedulePlanner class is the class that tests the web-ap. In
 * eclipse, this code is run to create the courses database. To test the web-ap,
 * courses are selected manually and then the FindLatestSchedule class is called
 * to maximize start time each day.
 */
public class AdvancedSchedulePlanner {

	public static void main(String[] args) {
		try {
			/**
			 * ONLY RUN BELOW CODE WHEN ADDING NEW COURSES TO DB - Recreates
			 * entire Database, doesn't just add one course to existing database
			 */
			// CreateCoursesDB.createCoursesDB();

			/**
			 * ONLY RUN BELOW CODE TO TEST DATABASE - Will query the courses in
			 * the database to print either the courses Monday start/end times,
			 * the number of sections per course, or will print out each day of
			 * the week for each class on a line, with 0's indicating no class
			 * during time slot and 1's indicating class time
			 * (DatabaseToString.databaseToString).
			 */
			// DatabaseToString.startAndEndTimesToString(); //Start and end
			// times of each day
			// DatabaseToString.numSectionsToString();
			// DatabaseToString.databaseToString();

			/**
			 * ONLY RUN CODE BELOW TO TEST ALGORITHM TO FIND LATEST START TIME
			 * EACH DAY - The courses need to be entered exactly as they are
			 * written in the courses folder, accessed in CreateCoursesDB.
			 * Courses are: "MUS 156", "CHEM 103", "MATH 240", "CS 302",
			 * "CHEM 104", "ECE 252", "ANTH 104", "CHEM 343", "CS 435", "CS 524"
			 * . Schedule is printed to console using ScheduleToPrint class.
			 */
			String[] courses = { "MUS 156", "MATH 240", "CHEM 103" };
			String[] optimizedSchedule = FindLatestSchedule.findLatestSchedule(courses);
			ScheduleToPrint.scheduleToPrint(optimizedSchedule);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
