package com.example.utils;

import com.example.model.MarsCity;
import com.example.model.MarsCity.LANE;
import com.example.model.MetroRoute;
import com.example.model.StationInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class MetroUtils {


	public static MetroRoute getBestPath(StationInfo source,
										 StationInfo destination,
										 ArrayList<MetroRoute> paths) {
		calculateEfficientRoute(source,destination,paths);
		Collections.sort(paths, new MapRouteComparator(true));
		printStationRoutes(paths);
		if (paths.size() > 0) {
			return paths.get(0);
		}
		return null;
	}


	private static void calculateEfficientRoute(StationInfo source,
												StationInfo destination,
												ArrayList<MetroRoute> paths) {
		if (source.equals(destination)) {
			return;
		}
		MetroRoute metroRoute = new MetroRoute(source, destination, null);
		metroRoute.setRouteStations(extractSameLaneStations(source, destination));
		if (metroRoute.getStations() != null && metroRoute.getStations().size() > 0) {
			// We found the path
			paths.add(metroRoute);
			return;
		}

		ArrayList<ArrayList<StationInfo>> switchersOnRoute = new ArrayList<>();
		getSwitchersPath(source,
						 source.getCurrentLane(),
						 destination.getCurrentLane(),
						 switchersOnRoute,
						 new ArrayList<StationInfo>(),
						 new ArrayList<StationInfo>());

//		printStationInfos(switchersOnRoute);

		for (int i = 0; i < switchersOnRoute.size(); i++) {
			paths.add(new MetroRoute(source, destination, switchersOnRoute.get(i)));
		}
		validatePaths(paths);
	}

	private static void getSwitchersPath(StationInfo source,
										 LANE srcLane,
										 LANE destLane,
										 ArrayList<ArrayList<StationInfo>> switchersPaths,
										 ArrayList<StationInfo> stationInfos,
										 ArrayList<StationInfo> switcherVisited) {
		// We don't have to switch back to our original lane
		if (source.getSwitchLane() == srcLane) {
			return;
		}
		if (source.getSwitchLane() == destLane || source.getCurrentLane() == destLane) {
			switchersPaths.add((ArrayList<StationInfo>)stationInfos.clone());
			return;
		}

		ArrayList<StationInfo> switchers = getSwitchers(source);
		switchers.removeAll(switcherVisited);
		for (StationInfo stationInfo : switchers) {
			switcherVisited.add(source);
			stationInfos.add(stationInfo);
			getSwitchersPath(stationInfo,
					srcLane,
					destLane,
					switchersPaths,
					stationInfos,
					switcherVisited);
			stationInfos.remove(stationInfo);
		}

	}

	/**
	 * Get switchers on the lane except the source if its a switcher
	 * @param source
	 * @return
	 */
	private static ArrayList<StationInfo> getSwitchers(StationInfo source) {
		ArrayList<StationInfo> laneStations = null;
		if (source.isSwitcher()) {
			// Add All the nearby switches
			laneStations = MarsCity.getInstance().getStationsBYLane(source.getCurrentLane());
			ArrayList<StationInfo> list = MarsCity.getInstance().getStationsBYLane(source.getSwitchLane());
			for (StationInfo stationInfo : list) {
				if (stationInfo.isSwitcher() && !laneStations.contains(stationInfo)) {
					laneStations.add(stationInfo);
				}
			}
		} else {
			laneStations = MarsCity.getInstance().getStationsBYLane(source.getCurrentLane());
		}
		ArrayList<StationInfo> filtered = new ArrayList<>();
		for (StationInfo stationInfo : laneStations) {
			if(stationInfo.isSwitcher()) {
				filtered.add(stationInfo);
			}
		}
		// Source switcher not needed, Since source would be added
		// Extra, to calculate the path
		if (source.isSwitcher()) {
			filtered.remove(source);
		}
		return filtered;
	}

	public static ArrayList<StationInfo> extractSameLaneStations(StationInfo source,
																 StationInfo destination) {
		LANE lane = getCommonLane(source, destination);
		if (lane == null) {
			return null;
		}
		ArrayList<StationInfo> laneStations = MarsCity.getInstance().getStationsBYLane(lane);
		ArrayList<StationInfo> filtered = new ArrayList<>();
		int sourceIndex = laneStations.indexOf(source);
		int destinationIndex = laneStations.indexOf(destination);
		if (sourceIndex < destinationIndex) {
			for (int i = sourceIndex; i <= destinationIndex; i++) {
				filtered.add(laneStations.get(i));
			}
		} else {
			for (int i = sourceIndex; i >= destinationIndex; i--) {
				filtered.add(laneStations.get(i));
			}
		}
		return filtered;
	}

	private static LANE getCommonLane(StationInfo source,
									  StationInfo destination) {
		if (source.getCurrentLane() == destination.getCurrentLane()) {
			return source.getCurrentLane();
		} else if (source.getCurrentLane() == destination.getSwitchLane()) {
			return source.getCurrentLane();
		} else if (source.getSwitchLane() == destination.getCurrentLane()) {
			return source.getSwitchLane();
		} else if (source.getSwitchLane() == destination.getCurrentLane()) {
			return source.getSwitchLane();
		}
		return null;
	}

	private static void validatePaths(ArrayList<MetroRoute> paths) {
		Iterator<MetroRoute> iterator = paths.iterator();
		while(iterator.hasNext()) {
			MetroRoute metroRoute = iterator.next();
			if (!validRoute(metroRoute)) {
				iterator.remove();
			}
		}
	}

	private static boolean validRoute(MetroRoute metroRoute) {
		ArrayList<StationInfo> stationInfos = metroRoute.getStations();
		for (int i = 0; i < stationInfos.size() - 1; i++) {
			ArrayList<StationInfo> infos = extractSameLaneStations(stationInfos.get(i),stationInfos.get(i+1));
			if ( infos != null
					&& infos.size() > 2) {
				return false;
			}
		}
		return true;
	}


	private static void printStationInfos(ArrayList<ArrayList<StationInfo>> paths) {
		if (paths.size() == 0) {
			System.out.println("No Stations");
			return;
		}
		for (int i = 0; i < paths.size(); i++) {
			System.out.println("Path " + (i +1));
			for (StationInfo stationInfo : paths.get(i)) {
				System.out.println(stationInfo);
			}
			System.out.println("------------------------");
		}
	}

	private static void printStationRoutes(ArrayList<MetroRoute> paths) {
		if (paths.size() == 0) {
			System.out.println("No Stations");
			return;
		}
		for (MetroRoute metroRoute : paths) {
			System.out.println(metroRoute);
		}

	}

	private static void printRoute(ArrayList<StationInfo> routes) {
		if (routes.size() == 0) {
			System.out.println("No Routes");
			return;
		}
		for (int i = 0; i < routes.size(); i++) {
//			System.out.println("Route " + (i+1));
			System.out.println(routes.get(i));
		}

	}
}
