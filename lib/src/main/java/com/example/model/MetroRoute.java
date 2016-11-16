package com.example.model;

import com.example.utils.MetroUtils;

import java.util.ArrayList;


public class MetroRoute {
	
	private ArrayList<StationInfo> mStations;
	private StationInfo mSource;
	private StationInfo mDestination;
	private int mPrice;
	private int mTime;
	private final int cTime = 5;//5 min for each station
	
	
	public MetroRoute(StationInfo source,
					  StationInfo dest,
					  ArrayList<StationInfo> switchersOnRoute) {
		mSource = source;
		mDestination = dest;
		mStations = new ArrayList<>();
		if (switchersOnRoute!= null) {
			buildPath(switchersOnRoute);
		}
		
	}


	private void buildPath(ArrayList<StationInfo> switchersOnRoute) {
		//If the switcher doesnot have source add it
		if(!switchersOnRoute.get(0).equals(mSource)){
			switchersOnRoute.add(0, mSource);
		}
		if (!switchersOnRoute.get(switchersOnRoute.size() - 1).equals(mDestination)){
			switchersOnRoute.add(mDestination);
		}
//		MetroMap.printRoute(switchersOnRoute);
		int stations = switchersOnRoute.size();
		for (int i = 0; i < stations - 1; i++) {
			addStations(MetroUtils.extractSameLaneStations(
											 switchersOnRoute.get(i),
											 switchersOnRoute.get(i + 1)));
		}
//		MetroMap.printRoute(mStations);
		buildRouteProperties();
		
	}
	
	/**
	 * This method is helpful when we have source and destination on same lane
	 * @param stationInfos
	 */
	public void setRouteStations(ArrayList<StationInfo> stationInfos) {
		mStations = stationInfos;
		if (mStations != null) {
			buildRouteProperties();
		}
	}
	
	public void buildRouteProperties() {
		calculatePrice();
		calculateTime();
	}
	
	private void calculateTime() {
		mTime = mStations.size();
		mTime--;// Since we need edges;
		mTime *= cTime;
	}


	private void calculatePrice() {
		int stations_count = mStations.size();
		mPrice = mStations.size();
		// On Lane Switch Extra pricing
		for (int i = 0; i < stations_count - 1; i++) {
			if (mStations.get(i).isSwitcher()) {
				if (mStations.get(i).getCurrentLane() != mStations.get(i + 1).getCurrentLane()) {
					mPrice++;
				}
			}
		}
		mPrice--; // Since we need edges;
	}


	private void addStations(ArrayList<StationInfo> stations){
		for (StationInfo stationInfo : stations) {
			if (!mStations.contains(stationInfo)){
				mStations.add(stationInfo);
			}
		}
	}
	
	/**
	 * @return the mStations
	 */
	public ArrayList<StationInfo> getStations() {
		return mStations;
	}


	/**
	 * @return the Source
	 */
	public StationInfo getSource() {
		return mSource;
	}


	/**
	 * @return the Destination
	 */
	public StationInfo getDestination() {
		return mDestination;
	}


	/**
	 * @return the Price
	 */
	public int getPrice() {
		return mPrice;
	}


	/**
	 * @return the Time
	 */
	public int getTime() {
		return mTime;
	}
	
	@Override
	public String toString() {
		return  "Source: " + mSource +
				" Destination:" + mDestination +
				printStations() +
				" \nPrice: " + mPrice + 
				" Time: " + mTime;
	}


	private String printStations() {
		StringBuilder builder = new StringBuilder();
		for (StationInfo stationInfo : mStations) {
			builder.append(" ");
			builder.append(stationInfo.toString());
		}
		return builder.toString();
	}
	
}
