package com.thoughtworks.utils;

import com.thoughtworks.model.MarsCity;
import com.thoughtworks.model.StationInfo;

import java.util.ArrayList;

/**
 * Stations data.
 */
public class StationsDataUtils {

	/**
	 * Gets all stations.
	 *Build the stations list as header and child form.
	 * @return all stations list
	 */
	public static ArrayList<StationInfo> getAllStations() {
		ArrayList<StationInfo> stations = new ArrayList<>();
		stations.add(new StationInfo(-1, MarsCity.LANE.RED.name(), false, MarsCity.LANE.RED));
		stations.addAll(MarsCity.getInstance().getStationsBYLane(MarsCity.LANE.RED));
		// RED Lane Added
		stations.add(new StationInfo(-2, MarsCity.LANE.GREEN.name(), false, MarsCity.LANE.GREEN));
		stations.addAll(MarsCity.getInstance().getStationsBYLane(MarsCity.LANE.GREEN));
		// GREEN Lane Added
		stations.add(new StationInfo(-3, MarsCity.LANE.BLUE.name(), false, MarsCity.LANE.BLUE));
		stations.addAll(MarsCity.getInstance().getStationsBYLane(MarsCity.LANE.BLUE));
		// BLUE Lane Added
		stations.add(new StationInfo(-4, MarsCity.LANE.BLACK.name(), false, MarsCity.LANE.BLACK));
		stations.addAll(MarsCity.getInstance().getStationsBYLane(MarsCity.LANE.BLACK));
		// BLACK Lane Added
		stations.add(new StationInfo(-5, MarsCity.LANE.YELLOW.name(), false, MarsCity.LANE.YELLOW));
		stations.addAll(MarsCity.getInstance().getStationsBYLane(MarsCity.LANE.YELLOW));
		// YELLOW Lane Added
//		stations.add(new StationInfo(-6, MarsCity.LANE.ORANGE.name(), false, MarsCity.LANE.ORANGE));
//		stations.addAll(MarsCity.getInstance().getStationsBYLane(MarsCity.LANE.ORANGE));
//		// ORANGE Lane Added
		return  stations;
	}

}
