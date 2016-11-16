package com.example.model;
import java.util.ArrayList;
import java.util.HashMap;

public class MarsCity {
	
	private static MarsCity marsCity;
	
	public static MarsCity getInstance(){
		if (marsCity == null) {
			return new MarsCity();
		}
		return marsCity;
	}
	
	private MarsCity(){
		fillStations();
	}
	
	public enum LANE {
		RED,// 0
		GREEN,// 1
		BLUE,// 2
		BLACK,// 3
		YELLOW;// 4
		
	}
	
	private final HashMap<LANE, ArrayList<StationInfo>> stationMap = new HashMap<>();
	
	public ArrayList<StationInfo> getStationsBYLane(LANE lane) {
		return (ArrayList<StationInfo>)stationMap.get(lane).clone();
	}
	
	public void fillStations() {
		ArrayList<StationInfo> lane;
		// Fill RED lane
		lane = new ArrayList<>();
		lane.add(new StationInfo(1, "Matrix Stand", false, LANE.RED));
		lane.add(new StationInfo(2, "Keymakers Lane", false, LANE.RED));
		lane.add(new StationInfo(3, "Oracle Lane", false, LANE.RED));
		lane.add(new StationInfo(4, "Boxing Avenue", true, LANE.RED, LANE.BLUE));
		lane.add(new StationInfo(5, "Cypher Lane", false, LANE.RED));
		lane.add(new StationInfo(6, "Smith Lane", false, LANE.RED));
		lane.add(new StationInfo(7, "Morpheus Lane", true, LANE.RED, LANE.YELLOW));
		lane.add(new StationInfo(8, "Trinity Lane", false, LANE.RED));
		lane.add(new StationInfo(9, "Neo Lane", true, LANE.RED, LANE.BLACK));
		// add to map
		stationMap.put(LANE.RED, lane);
		// Fill GREEN lane
		lane = new ArrayList<>();
		lane.add(new StationInfo(11, "North Pole", false, LANE.GREEN));
		lane.add(new StationInfo(12, "Sheldon Street", false, LANE.GREEN));
		lane.add(new StationInfo(13, "Greenland", false, LANE.GREEN));
		lane.add(new StationInfo(14, "City Centre", true, LANE.GREEN, LANE.BLUE));
		lane.add(new StationInfo(15, "Stadium House", false, LANE.GREEN));
		lane.add(new StationInfo(16, "Green House", false, LANE.GREEN));
		lane.add(new StationInfo(17, "Green Cross", true, LANE.GREEN, LANE.YELLOW));
		lane.add(new StationInfo(18, "South Pole", false, LANE.GREEN));
		lane.add(new StationInfo(19, "South Park", true, LANE.GREEN, LANE.BLACK));
		// add to map
		stationMap.put(LANE.GREEN, lane);
		// Fill BLUE lane
		lane = new ArrayList<>();
		lane.add(new StationInfo(21, "East End", true, LANE.BLUE, LANE.BLACK));
		lane.add(new StationInfo(22, "Foot Stand", false, LANE.BLUE));
		lane.add(new StationInfo(23, "Football Stadium", false, LANE.BLUE));
		lane.add(new StationInfo(14, "City Centre", true, LANE.BLUE, LANE.GREEN));
		lane.add(new StationInfo(25, "Peter Park", false, LANE.BLUE));
		lane.add(new StationInfo(26, "Maximus", false, LANE.BLUE));
		lane.add(new StationInfo(27, "Rocky Street", false, LANE.BLUE));
		lane.add(new StationInfo(28, "Boxers Street", false, LANE.BLUE));
		lane.add(new StationInfo(4, "Boxing Avenue", true, LANE.BLUE, LANE.RED));
		lane.add(new StationInfo(30, "West End", false, LANE.BLUE));
		// add to map
		stationMap.put(LANE.BLUE, lane);
		// Fill BLACK lane
		lane = new ArrayList<>();
		lane.add(new StationInfo(21, "East End", true, LANE.BLACK, LANE.BLUE));
		lane.add(new StationInfo(32, "Gotham Street", false, LANE.BLACK));
		lane.add(new StationInfo(33, "Batman Street", false, LANE.BLACK));
		lane.add(new StationInfo(34, "Jokers Street", false, LANE.BLACK));
		lane.add(new StationInfo(35, "Hawkins Street", false, LANE.BLACK));
		lane.add(new StationInfo(36, "Da Vinci Lane", false, LANE.BLACK));
		lane.add(new StationInfo(19, "South Park", true, LANE.BLACK, LANE.GREEN));
		lane.add(new StationInfo(38, "Newton Bath Tub", false, LANE.BLACK));
		lane.add(new StationInfo(39, "Einstein Lane", false, LANE.BLACK));
		lane.add(new StationInfo(9, "Neo Lane", true, LANE.BLACK, LANE.RED));
		// add to map
		stationMap.put(LANE.BLACK, lane);
		// Fill YELLOW lane
		lane = new ArrayList<>();
		lane.add(new StationInfo(16, "Green Cross", true, LANE.YELLOW, LANE.GREEN));
		lane.add(new StationInfo(42, "Orange Street", false, LANE.YELLOW));
		lane.add(new StationInfo(43, "Silk Board", false, LANE.YELLOW));
		lane.add(new StationInfo(44, "Snake Park", false, LANE.YELLOW));
		lane.add(new StationInfo(7, "Morpheus Lane", true, LANE.YELLOW, LANE.RED));
		lane.add(new StationInfo(46, "Little Street", false, LANE.YELLOW));
		lane.add(new StationInfo(47, "Cricket Grounds", false, LANE.YELLOW));
		// add to map
		stationMap.put(LANE.YELLOW, lane);
		
		
	}
	
	
}
