package com.example.nursescheduling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nursescheduling.exception.ResourceNotFoundException;
import com.example.nursescheduling.model.FrontEndData;
import com.example.nursescheduling.model.Nurse;
import com.example.nursescheduling.repository.FrontEndDataRepository;
import com.example.nursescheduling.repository.NurseRepository;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class NurseController {
	@Autowired
	private NurseRepository nurseRepository;

	@Autowired
	private FrontEndDataRepository frontEndDataRepository;

	@GetMapping("/nurses")
	public List<FrontEndData> getNurse() {
		return frontEndDataRepository.findAll();
	}

	// get employee by id rest api
	@GetMapping("/nurses/{id}")
	public ResponseEntity<Nurse> getNurseById(@PathVariable Long id) {
		Nurse nurse = nurseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nurse not exist with id :" + id));
		return ResponseEntity.ok(nurse);
	}

	// create employee rest api
	@PostMapping("/nurses")
	public void createNurse(@RequestBody FrontEndData frontEndData) {
		System.out.println(frontEndData.toString());
		frontEndDataRepository.save(frontEndData);
		int period = 30;
		String numNurse;
		String Morning;
		String Evening;
		String Night;
		String Off;
		System.out.println("First Starting-------");
		System.out.println(frontEndData.toString());
		numNurse = frontEndData.getTotalNurse();
		Off = frontEndData.getOff();
		Morning = frontEndData.getMorning();
		Evening = frontEndData.getEvening();
		Night = frontEndData.getNight();
		System.out.println("#######&&&*******");
		System.out.println("Hellloooo  " + numNurse);
		System.out.println("Hellloooo  " + Off);
		System.out.println("Hellloooo  " + Morning);
		System.out.println("Hellloooo  " + Night);
		System.out.println("Hellloooo  " + Evening);

		Model model = new Model("Nurse Scheduling");
		IntVar[][] roster = model.intVarMatrix("poster", period, Integer.parseInt(numNurse), 0, 3);
		int pattern[] = { Integer.parseInt(Off), Integer.parseInt(Morning), Integer.parseInt(Evening),
				Integer.parseInt(Night) }; // {off,Morning,Evening,Night}

		// int values[]= {0,1,2,3};
		IntVar[] values = model.intVarArray("values", 4, new int[] { 0, 1, 2, 3 });

		// variable declaration
		String shift[] = { "O", "M", "A", "N" }; // {off,Morning,Afternoon,Night}
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
		for (int i = 0; i < Integer.parseInt(numNurse); i++) {
			IntVar total = model.intVar("Nurse" + (i + 1), 5, 8);
			// System.out.println("Total is " + total.getValue());
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
			for (IntVar[] row : roster) {
				String[] nurse1 = new String[10];
				int i = 0;
				for (IntVar box : row) {
					if (box.getValue() == 0) {
						nurse1[i] = "O";
						i++;
					} else if (box.getValue() == 1) {
						nurse1[i] = "M";
						i++;
					}

					else if (box.getValue() == 2) {
						nurse1[i] = "E";
						i++;
					}

					else {
						nurse1[i] = "N";
						i++;
					}
				}
				Nurse nurseSchedule = new Nurse(nurse1[0], nurse1[1], nurse1[2], nurse1[3], nurse1[4], nurse1[5],
						nurse1[6], nurse1[7], nurse1[8], nurse1[9]);
				nurseRepository.save(nurseSchedule);

			}
		} else {
			System.out.println("No Solution Found");
		}
	}
}
