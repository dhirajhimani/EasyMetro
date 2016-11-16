package com.thoughtworks.model;

import com.thoughtworks.utils.MetroUtils;

import java.util.ArrayList;

/**
 * Metro route.
 */
public class MetroRoute {
	
	private ArrayList<StationInfo> mStations;
	private StationInfo mSource;
	private StationInfo mDestination;
	private int mPrice;
	private int mTime;
	private final int cTime = 5;//5 min for each station
	private String mRouteInfo;

	/**
	 * Instantiates a new Metro route.
	 *
	 * @param source           the source
	 * @param dest             the dest
	 * @param switchersOnRoute the switchers on route
	 */
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

	/**
	 * We have all the lanes through which we can change to other lanes and reach
	 * our destination.
	 * Now since we have lanes b/w src and dest just need to add them to build the path.
	 * @param switchersOnRoute // lanes b/w src and dest
	 */
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
	 * instead of using inb/w lanes we have all stations since they are
	 * on same route
	 * @param stationInfos the station infos
	 */
	public void setRouteStations(ArrayList<StationInfo> stationInfos) {
		mStations = stationInfos;
		if (mStations != null) {
			buildRouteProperties();
		}
	}

	/**
	 * Build route properties.
	 */
	public void buildRouteProperties() {
		calculatePrice();
		calculateTime();
	}
	
	private void calculateTime() {
		mTime = mStations.size();
		mTime--;// Since we need edges;
		mTime *= cTime; // Total time
	}


	private void calculatePrice() {
		int stations_count = mStations.size();
		mPrice = mStations.size();
		// On Lane Switch Extra pricing
		for (int i = 0; i < stations_count - 1; i++) {
			if (mStations.get(i).isSwitcher()) {
				if (mStations.get(i).getCurrentLane() != mStations.get(i + 1).getCurrentLane()) {
					mPrice++;//Switching lanes cost extra
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
	 * Gets stations.
	 *
	 * @return the mStations
	 */
	public ArrayList<StationInfo> getStations() {
		return mStations;
	}


	/**
	 * Gets source.
	 *
	 * @return the Source
	 */
	public StationInfo getSource() {
		return mSource;
	}


	/**
	 * Gets destination.
	 *
	 * @return the Destination
	 */
	public StationInfo getDestination() {
		return mDestination;
	}


	/**
	 * Gets price.
	 *
	 * @return the Price
	 */
	public int getPrice() {
		return mPrice;
	}


	/**
	 * Gets time.
	 *
	 * @return the Time
	 */
	public int getTime() {
		return mTime;
	}
	
	/**
	 * Gets route info.
	 * Since we can save the multiple calls here.
	 * @return the route info
	 */
	public String getRouteInfo() {
		if (mRouteInfo == null) {
			mRouteInfo = "Source: " + mSource +
						 "\nDestination:" + mDestination +
						 "\n\n" +
						 printStations() +
						 "\n\nPrice: " + mPrice + "$" +
						 " Time: " + mTime + "min";
		}
		return mRouteInfo;
	}

	/**
	 * The way we want to print the stations
	 */
	private String printStations() {
		StringBuilder builder = new StringBuilder();
		int stations_count = mStations.size();
		// On Lane Switch Extra pricing
		for (int i = 0; i < stations_count - 1; i++) {
			builder.append(mStations.get(i).toString());
			if (mStations.get(i).getCurrentLane() != mStations.get(i + 1).getCurrentLane()) {
				builder.append(" (Change here for " +  mStations.get(i + 1).getCurrentLane().name().toLowerCase() + " lane)");
			}
			builder.append(" " + MetroUtils.cRouteStraightArrow + " ");
		}
		builder.append(mStations.get(stations_count - 1).toString());
		return builder.toString();
	}
}
