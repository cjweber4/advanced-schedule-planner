import java.util.ArrayList;

import scpsolver.constraints.LinearSmallerThanEqualsConstraint;
import scpsolver.lpsolver.LinearProgramSolver;
import scpsolver.lpsolver.SolverFactory;
import scpsolver.problems.LinearProgram;

public class FindLatestSchedule {
	private static LinearProgramSolver solver;

	public static String[] findLatestSchedule(String[] courses) throws Exception {
		LinearProgram lp = GenerelLPConstraints.generalLPConstraints(courses);
		int mondayTotalSections = GenerelLPConstraints.mondayTotalSections;
		//System.out.println(mondayTotalSections);
		int tuesdayTotalSections = GenerelLPConstraints.tuesdayTotalSections;
		int wednesdayTotalSections = GenerelLPConstraints.wednesdayTotalSections;
		int thursdayTotalSections = GenerelLPConstraints.thursdayTotalSections;
		int fridayTotalSections = GenerelLPConstraints.fridayTotalSections;
		int totalNumSections = GenerelLPConstraints.totalNumSections;
		double[] timesComp = GenerelLPConstraints.timesComp;
		int p = GenerelLPConstraints.p;
		ArrayList<Integer> mondayStartTimes = GenerelLPConstraints.mondayStartTimes;
		ArrayList<Integer> tuesdayStartTimes = GenerelLPConstraints.tuesdayStartTimes;
		ArrayList<Integer> wednesdayStartTimes = GenerelLPConstraints.wednesdayStartTimes;
		ArrayList<Integer> thursdayStartTimes = GenerelLPConstraints.thursdayStartTimes;
		ArrayList<Integer> fridayStartTimes = GenerelLPConstraints.fridayStartTimes;
		ArrayList<String> sections = GenerelLPConstraints.sections;

		//////////////// maximin startTime: for all enrolled section, we choose
		//////////////// the minimum course start time as our startTime,because
		//////////////// we are trying to maximize start time
		int sectionCount = 0;
		ArrayList<double[]> constraintHold = new ArrayList<double[]>();
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 5] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 4] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 3] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 2] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		for (int i = 0; i < mondayTotalSections; i++) {
			double[] hold = new double[totalNumSections + 5];
			hold[sectionCount] = -timesComp[sectionCount];
			hold[hold.length - 1] = 1;
			constraintHold.add(hold);
			sectionCount++;
		}
		//System.out.println(constraintHold.size());
		for (int i = 0; i < constraintHold.size(); i++) {
			String constraintName = "c" + Integer.toString(p);
			lp.addConstraint(new LinearSmallerThanEqualsConstraint(constraintHold.get(i), 2300, constraintName));
			p++;

		}
		double[] sol = {};
		lp.setMinProblem(false);
		try{
			//System.out.println("here");
			//if(Importer.hitCount==1){
				//System.out.println("here");

			solver = SolverFactory.newDefault();
			////}
			//System.out.print(solver.toString());
			 sol = solver.solve(lp);

		}catch(Exception e){
			return null;
			
		}

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
		//System.out.println(startTimes);
		String[] optimizedSchedule = new String[courses.length];
		int count = 0;
		for (int i = 0; i < sol.length / 5; i++) {
			if (i >= sol.length - 5) {
				//System.out.println(sol[i]);
			} else if (sol[i] == 1) {
				if (i < mondayTotalSections) {
					//System.out.println(sections.get(i) + ": Start Time =" + startTimes.get(i));
					optimizedSchedule[count] = sections.get(i);
					count++;
				} else {
					//System.out.println(sections.get(i % mondayTotalSections) + ": Start Time =" + startTimes.get(i));
					optimizedSchedule[count] = sections.get(i % mondayTotalSections);
					count++;
				}
			}
		}

		//System.out.println(optimizedSchedule[0]);
		return optimizedSchedule;

	}
}
