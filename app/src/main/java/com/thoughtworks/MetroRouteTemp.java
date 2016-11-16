package com.thoughtworks;

import com.thoughtworks.model.MarsCity;
import com.thoughtworks.model.MetroRoute;
import com.thoughtworks.model.StationInfo;
import com.thoughtworks.utils.MetroUtils;

/**
 * Created by dhirajhimani on 9/20/2016.
 */

public class MetroRouteTemp {

	public static void main(String[] args) {
		//Test 1
//		StationInfo source = (new StationInfo(21, "East End", true, LINE.BLUE, LINE.BLACK));
//		StationInfo destination = (new StationInfo(25, "Peter Park", false, LINE.BLUE));
		// 4 20

		//Test 2
		StationInfo source = (new StationInfo(9, "Neo Lane", true, MarsCity.LANE.RED, MarsCity.LANE.BLACK));
		StationInfo destination = (new StationInfo(43, "Silk Board", false, MarsCity.LANE.YELLOW));
		// 6 25

		//Test 3
//		StationInfo source = (new StationInfo(15, "Stadium House", false, LINE.GREEN));
//		StationInfo destination = (new StationInfo(21, "East End", true, LINE.BLUE, LINE.BLACK));
		// 5 20
		MetroRoute metroRoute = MetroUtils.getBestPath(source, destination);

	}
}
