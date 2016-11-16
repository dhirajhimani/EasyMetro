package com.thoughtworks.utils;

import com.thoughtworks.model.MarsCity;
import com.thoughtworks.model.MarsCity.LANE;
import com.thoughtworks.model.MetroRoute;
import com.thoughtworks.model.StationInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Metro utils.
 */
public class MetroUtils {

	/**
	 * The constant cRouteStraightArrow.
	 */
	public static final String cRouteStraightArrow = "->";

	/**
	 * Gets best path.
	 *
	 * @param source      the source
	 * @param destination the destination
	 * @return the best path
	 */
	public static MetroRoute getBestPath(StationInfo source,
								   StationInfo destination) {
		ArrayList<MetroRoute> paths = new ArrayList<>();
		calculateEfficientRoute(source,destination,paths);
		Collections.sort(paths, new MapRouteComparator(true));
		if (paths.size() > 0) {
			return paths.get(0);
		}
		return null;
	}

	/**
	 * Find all the paths from src to dest
	 * Case 1. is source and destination same
	 * Case 2. src and dest on same lane
	 * Case 3. Find all possible routes
	 * @param source // Source Station
	 * @param destination // Destination Station
	 * @param paths // All the path b/w src and dest
	 */
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

	/**
	 * Find all possible routes of switchers, than calculated the final paths
	 * @param source // Source Station
	 * @param srcLane // Source lane
	 * @param destLane // dest lane
	 * @param switchersPaths // Switchers on the path calculated
	 * @param stationInfos	// Recursively save Paths
	 * @param switcherVisited // If the lane is already visited
	 */
	private static void getSwitchersPath(StationInfo source,
										 LANE srcLane,
										 LANE destLane,
										 ArrayList<ArrayList<StationInfo>> switchersPaths,
										 ArrayList<StationInfo> stationInfos,
										 ArrayList<StationInfo> switcherVisited) {
		// We don't have to switch back to our original lane
		if (source.getSwitchLane().contains(srcLane)) {
			return;
		}
		if (source.getSwitchLane().contains(destLane) || source.getCurrentLane() == destLane) {
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
	 * Get switchers on the lane and if the source is switcher
	 * Get the nearby switchers also.
	 * @param source // Source Station
	 * @return // all switchers connected to source
	 */
	private static ArrayList<StationInfo> getSwitchers(StationInfo source) {
		ArrayList<StationInfo> laneStations;
		if (source.isSwitcher()) {
			// Add All the nearby switches
			laneStations = MarsCity.getInstance().getStationsBYLane(source.getCurrentLane());
			List<LANE> switchLanes = source.getSwitchLane();
			for (LANE lane : switchLanes) {
				ArrayList<StationInfo> list = MarsCity.getInstance().getStationsBYLane(lane);
				for (StationInfo stationInfo : list) {
					if (stationInfo.isSwitcher() && !laneStations.contains(stationInfo)) {
						laneStations.add(stationInfo);
					}
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

	/**
	 * Extract same lane stations.
	 *
	 * @param source      the source
	 * @param destination the destination
	 * @return the array list
	 */
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

	/**
	 * Get common lane if any b/w src and dest otherwise return null
	 * @param source  // Source Station
	 * @param destination  // Destination Station
	 * @return // Common lane if any
	 */
	private static LANE getCommonLane(StationInfo source,
									  StationInfo destination) {
		if (source.getCurrentLane() == destination.getCurrentLane()) {
			return source.getCurrentLane();
		} else if (destination.getSwitchLane().contains(source.getCurrentLane())) {
			return source.getCurrentLane();
		} else if (source.getSwitchLane().contains(destination.getCurrentLane())) {
			return destination.getCurrentLane();
		}
//		else if (source.getSwitchLane() == destination.getCurrentLane()) {
//			return source.getSwitchLane();
//		}
		return null;
	}

	/**
	 * Validate paths as recursion produces all combinations of paths from @getSwitchersPath,
	 * @param paths // all routes
	 */
	private static void validatePaths(ArrayList<MetroRoute> paths) {
		Iterator<MetroRoute> iterator = paths.iterator();
		while(iterator.hasNext()) {
			MetroRoute metroRoute = iterator.next();
			if (!validRoute(metroRoute)) {
				iterator.remove();
			}
		}
	}

	/**
	 * Validate the Route is valid as the two stations on same lane would be in increasing order
	 * So the stations b/w nearby stations will result valid for two stations and if more than invalid.
	 * @param metroRoute // one of the route b/w src and dst paths
	 * @return // if valid true
	 */
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


//	/**
//	 * Print on console for testing
//	 * @param paths //
//	 */
//	private static void printStationInfos(ArrayList<ArrayList<StationInfo>> paths) {
//		if (paths.size() == 0) {
//			System.out.println("No Stations");
//			return;
//		}
//		for (int i = 0; i < paths.size(); i++) {
//			System.out.println("Path " + (i +1));
//			for (StationInfo stationInfo : paths.get(i)) {
//				System.out.println(stationInfo);
//			}
//			System.out.println("------------------------");
//		}
//	}
//
//	/**
//	 * Print on console for testing
//	 * @param paths //
//	 */
//	private static void printStationRoutes(ArrayList<MetroRoute> paths) {
//		if (paths.size() == 0) {
//			System.out.println("No Stations");
//			return;
//		}
//		for (MetroRoute metroRoute : paths) {
//			System.out.println(metroRoute);
//		}
//
//	}
//
//	/**
//	 * Print on console for testing
//	 * @param routes //
//	 */
//	private static void printRoute(ArrayList<StationInfo> routes) {
//		if (routes.size() == 0) {
//			System.out.println("No Routes");
//			return;
//		}
//		for (int i = 0; i < routes.size(); i++) {
////			System.out.println("Route " + (i+1));
//			System.out.println(routes.get(i));
//		}
//
//	}
}
