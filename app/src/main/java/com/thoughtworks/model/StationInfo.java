package com.thoughtworks.model;
import com.thoughtworks.model.MarsCity.LANE;

import java.util.Arrays;
import java.util.List;

/**
 * StationInfo as the name is the all the info abbout the station.
 * Name, we can change the lane or not, current lane etc.
 */
public class StationInfo {

	private int mId;
	private String mStationName;
	private boolean mIsSwitcher;
	private LANE mCurrentLane;
	private List<LANE> mSwitchLane;


//	/**
//	 * Instantiates a new Station info.
//	 *
//	 * @param id          the id
//	 * @param stationName the station name
//	 * @param isSwitcher  the is switcher
//	 * @param currentLane the current lane
//	 */
//	public StationInfo(int id,
//				String stationName,
//				boolean isSwitcher,
//				LANE currentLane) {
//
//		this(id, stationName, isSwitcher, currentLane, null);
//
//	}

	/**
	 * Instantiates a new Station info.
	 *
	 * @param id          the id
	 * @param stationName the station name
	 * @param isSwitcher  the is switcher
	 * @param currentLane the current lane
	 * @param switchLane  the switch lane
	 */
	public StationInfo(int id,
				String stationName,
				boolean isSwitcher,
				LANE currentLane,
				LANE... switchLane) {
		this.mId = id;
		this.mStationName = stationName;
		this.mIsSwitcher = isSwitcher;
		this.mCurrentLane = currentLane;
		if (switchLane != null) {
			this.mSwitchLane = Arrays.asList(switchLane);
		} else {
			this.mSwitchLane = null;
		}

}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationInfo stationInfo = (StationInfo) o;
        return (mStationName.equals(stationInfo.mStationName) );
    }

    @Override
    public int hashCode() {
        return mStationName.hashCode();
    }

    @Override
    public String toString() {
        return mStationName;
    }

	/**
	 * Is dummy boolean.
	 *
	 * @return the boolean
	 */
	public boolean isDummy() {
		return mId < 0;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public int getId() {
		return mId;
	}

	/**
	 * Gets station name.
	 *
	 * @return the station name
	 */
	public String getStationName() {
		return mStationName;
	}

	/**
	 * Can we switch lance.
	 *
	 * @return the boolean
	 */
	public boolean isSwitcher() {
		return mIsSwitcher;
	}

	/**
	 * Gets current lane.
	 *
	 * @return the current lane
	 */
	public LANE getCurrentLane() {
		return mCurrentLane;
	}

	/**
	 * Gets switch lane.
	 *
	 * @return the switch lane
	 */
	public List<LANE> getSwitchLane() {
		return mSwitchLane;
	}


}
