package com.example.nursescheduling.controller;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.nursescheduling.model.Nurse;
import com.example.nursescheduling.repository.NurseRepository;
import com.google.gson.Gson;

public class NurseScheduling {
	
	@Autowired
	private NurseRepository nurseRepository;
	
	public int period = 30;
	public int numNurse ;
	int M;
	int E;
	int N;
	int O;
	
	public NurseScheduling(int numNurse, int m, int e, int n, int o) {
		super();
		this.numNurse = numNurse;
		this.M = m;
		this.E = e;
		this.N = n;
		this.O = o;
	}

	Model model = new Model("Nurse Scheduling");
	IntVar[][] roster = model.intVarMatrix("poster", period, numNurse, 0, 3);
	int pattern[] = { O, M, E, N }; // {off,Morning,Evening,Night}

	// int values[]= {0,1,2,3};
	IntVar[] values = model.intVarArray("values", 4, new int[] { 0, 1, 2, 3 });

	// variable declaration
	String shift[] = { "O", "M", "A", "N" }; // {off,Morning,Afternoon,Night}

	public void show() {
		for (IntVar[] row : roster) {
			String [] nurse=new String[10];
			int i=0;
			for (IntVar box : row) {
				if (box.getValue() == 0) {
					nurse[i]="O";
					i++;
					}
				else if (box.getValue() == 1) {
					nurse[i]="M";
					i++;
				}
					
				else if (box.getValue() == 2) {
					nurse[i]="E";
					i++;
				}
					
				else {
					nurse[i]="N";
					i++;
				}
			}
			Nurse nurseSchedule=new Nurse(nurse[0],nurse[1],nurse[2],nurse[3],nurse[4],nurse[5],nurse[6],nurse[7],
					nurse[8],nurse[9]);
			nurseRepository.save(nurseSchedule);
			
		}

	}

	public void solve() {
		for (int i = 0; i < period; i++) {
			IntVar Oocc = model.intVar("Off" + i, pattern[0]);
			IntVar Mocc = model.intVar("Morning" + i, pattern[1]);
			IntVar Eocc = model.intVar("Afternoon" + i, pattern[2]);
			IntVar Nocc = model.intVar("Night" + i, pattern[3]);
			model.count(0, roster[i], Oocc).post();
			model.count(1, roster[i], Mocc).post();
			model.count(2, roster[i], Eocc).post();
			model.count(3, roster[i], Nocc).post();

		}
		// set approximately 1 day off in every 5 days
		for (int i = 0; i < numNurse; i++) {
			IntVar total = model.intVar("Nurse" + (i + 1), 5, 8);
			//System.out.println("Total is " + total.getValue());
			total.eq(roster[0][i].add(roster[1][i], roster[2][i], roster[3][i], roster[4][i])).post();
			total.eq(roster[5][i].add(roster[6][i], roster[7][i], roster[8][i], roster[9][i])).post();
			total.eq(roster[10][i].add(roster[11][i], roster[12][i], roster[13][i], roster[14][i])).post();
			total.eq(roster[15][i].add(roster[16][i], roster[17][i], roster[18][i], roster[19][i])).post();
			total.eq(roster[20][i].add(roster[21][i], roster[22][i], roster[23][i], roster[24][i])).post();
			total.eq(roster[25][i].add(roster[26][i], roster[27][i], roster[28][i], roster[29][i])).post();

			// make each nurse has shift change
			for (int j = 0; j < period - 1; j += 2) {
				model.allDifferent(roster[j][i], roster[j + 1][i]).post();
			}
		}
		boolean isSolve = model.getSolver().solve();
		if (isSolve) {
			show();
		} else {
			System.out.println("No Solution Found");
		}
	}
}
