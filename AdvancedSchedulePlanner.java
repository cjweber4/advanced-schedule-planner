/** Main class for testing app */

public class AdvancedSchedulePlanner {

	public static void main(String[] args) {
		try {
			// CreateCoursesDB.createCoursesDB(); // ONLY RUN WHEN ADDING NEW
			// COURSES TO DB - Recreates entire Database, doesn't just add one

			// DatabaseToString.startAndEndTimesToString(); //Start and end
			// times of each day
			// DatabaseToString.numSectionsToString();
			// DatabaseToString.databaseToString();
			// String[] n = { "CHEM 103 1", "CHEM 104 1" };
			// ScheduleToPrint.scheduleToPrint(n);
			
			// Courses to take
			 String[] courses = { "CHEM 103", "MATH 240", "CS 302", "ECE 252" };
			 String[] optimizedSchedule = FindLatestSchedule.findLatestSchedule(courses);
			 ScheduleToPrint.scheduleToPrint(optimizedSchedule);
			// for(int i = 0;i<optimizedSchedule.length;i++){
			// System.out.println(optimizedSchedule[i]);
			// }
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
