
///////////////////////////////////////////////////////////////////////////////
// Main Class:       AdvancedSchedulePlanner
// File:             FindLatestSchedule
//
// Author:           Curtis Weber
// Email:            cjweber4@wisc.edu
// Date:         	 6/2016
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

/**
 * FindLatestSchedule uses a linear optimization problem solver to find the
 * latest possible start time each day given a set courses to take.
 */
public class FindLatestSchedule {

	/**
	 * Class variable used from library SCPSolver. It is used to solve the
	 * optimization problem that maximizes start time.
	 */
	private static LinearProgramSolver solver;

	/**
	 * Sets up optimization problem to solve and solves it
	 * 
	 * @return Array with section number of each course to take.
	 */
	public static String[] findLatestSchedule(String[] courses) throws Exception {

		// Calling class GeneralLPConstraints to set up the linear problem to
		// solve with the correct number of constraints and variables.
		LinearProgram lp = GenerelLPConstraints.generalLPConstraints(courses);
		System.out.println("Here");


		// Variables to hold the total amount of sections possible on each day
		// of the week
		int mondayTotalSections = GenerelLPConstraints.mondayTotalSections;
		int tuesdayTotalSections = GenerelLPConstraints.tuesdayTotalSections;
		int wednesdayTotalSections = GenerelLPConstraints.wednesdayTotalSections;
		int thursdayTotalSections = GenerelLPConstraints.thursdayTotalSections;
		int fridayTotalSections = GenerelLPConstraints.fridayTotalSections;
		int totalNumSections = GenerelLPConstraints.totalNumSections;
		// Variable to hold the start time of each section each day
		double[] timesComp = GenerelLPConstraints.timesComp;
		// Variable that hold the total number of constraints in the
		// optimization problem
		int p = GenerelLPConstraints.p;
		// Variables to hold all the start times for each section each day
		ArrayList<Integer> mondayStartTimes = GenerelLPConstraints.mondayStartTimes;
		ArrayList<Integer> tuesdayStartTimes = GenerelLPConstraints.tuesdayStartTimes;
		ArrayList<Integer> wednesdayStartTimes = GenerelLPConstraints.wednesdayStartTimes;
		ArrayList<Integer> thursdayStartTimes = GenerelLPConstraints.thursdayStartTimes;
		ArrayList<Integer> fridayStartTimes = GenerelLPConstraints.fridayStartTimes;
		ArrayList<String> sections = GenerelLPConstraints.sections;

		/////////////////////////////////////////////////////////////////////////////
		/**
		 * maximize startTime: for all enrolled section, we choose the maximum
		 * course start time as our startTime
		 */
		// sectionCount is a counter that increases as each new section from
		// each course is added to
		int sectionCount = 0;
		ArrayList<double[]> constraintHold = new ArrayList<double[]>();
		// Constraint saying that the variable for Monday start time (the last
		// variable) needs to be equal to one of the given start times for the
		// sections. This is done by setting the constraint value of the start
		// time for monday to 1, and then the monday start times to negative
		// their values. This will make the variable equal to a positive start
		// time.
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 5] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		// Constraint saying that the variable for Tuesday start time (the
		// second last variable) needs to be equal to one of the given start
		// times for the sections.
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 4] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		// Constraint saying that the variable for Wednesday start time (the
		// third last variable) needs to be equal to one of the given start
		// times for the sections.
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 3] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		// Constraint saying that the variable for Thursday start time (the
		// fourth last variable) needs to be equal to one of the given start
		// times for the sections.
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 2] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		// Constraint saying that the variable for Friday start time (the
		// fifth last variable) needs to be equal to one of the given start
		// times for the sections.
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 1] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}

		// Adding the constraint to maximize the final five last variables.
		for (int i = 0; i < constraintHold.size(); i++) {
			String constraintName = "c" + Integer.toString(p);
			// Constraints are smaller than or equal to 2300 because all the
			// start times of classes are earlier than 11:00pm
			
			lp.addConstraint(new LinearSmallerThanEqualsConstraint(constraintHold.get(i), 2300, constraintName));
			p++;
		}
		
		// Sol: holds the values of the solved variables.
		double[] sol = {};
		// Setting the problem to maximize the values of the last five
		// variables.
		lp.setMinProblem(false);
		try {
			solver = SolverFactory.newDefault();
			sol = solver.solve(lp);

		} catch (Exception e) {
			return null;
		}

		// Matching the returned variables to the corresponding course and
		// section
		ArrayList<Integer> startTimes = new ArrayList<Integer>();
		for (int i = 0; i < mondayTotalSections; i++) {
			startTimes.add(mondayStartTimes.get(i));
		}
		for (int i = 0; i < tuesdayTotalSections; i++) {
			startTimes.add(tuesdayStartTimes.get(i));
		}
		for (int i = 0; i < wednesdayTotalSections; i++) {
			startTimes.add(wednesdayStartTimes.get(i));
		}
		for (int i = 0; i < thursdayTotalSections; i++) {
			startTimes.add(thursdayStartTimes.get(i));
		}
		for (int i = 0; i < fridayTotalSections; i++) {
			startTimes.add(fridayStartTimes.get(i));
		}
		String[] optimizedSchedule = new String[courses.length];
		int count = 0;
		for (int i = 0; i < sol.length / 5; i++) {
			if (i >= sol.length - 5) {
			} else if (sol[i] == 1) {
				if (i < mondayTotalSections) {
					optimizedSchedule[count] = sections.get(i);
					count++;
				} else {
					optimizedSchedule[count] = sections.get(i % mondayTotalSections);
					count++;
				}
			}
		}

		return optimizedSchedule;

	}
}
