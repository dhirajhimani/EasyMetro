package com.example;


import com.example.model.MarsCity.LANE;
import com.example.model.MetroRoute;
import com.example.model.StationInfo;
import com.example.utils.MetroUtils;

import java.util.ArrayList;

public class TempMarsCalculator {

	public static void main(String[] args) {
		//Test 1
//		StationInfo source = (new StationInfo(21, "East End", true, LINE.BLUE, LINE.BLACK));
//		StationInfo destination = (new StationInfo(25, "Peter Park", false, LINE.BLUE));
		// 4 20

		//Test 2
		StationInfo source = (new StationInfo(9, "Neo Lane", true, LANE.RED, LANE.BLACK));
		StationInfo destination = (new StationInfo(43, "Silk Board", false, LANE.YELLOW));
		// 6 25

		//Test 3
//		StationInfo source = (new StationInfo(15, "Stadium House", false, LINE.GREEN));
//		StationInfo destination = (new StationInfo(21, "East End", true, LINE.BLUE, LINE.BLACK));
		// 5 20
		ArrayList<MetroRoute> paths = new ArrayList<>();
		MetroRoute metroRoute = MetroUtils.getBestPath(source, destination, paths);

	}
}

