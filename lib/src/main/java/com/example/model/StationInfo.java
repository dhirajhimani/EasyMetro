package com.example.model;
import com.example.model.MarsCity.LANE;

public class StationInfo {

	private int mId;
	private String mStationName;
	private boolean mIsSwitcher;
	private LANE mCurrentLane;
	private LANE mSwitchLane;
	
	
	public StationInfo(int id,
				String stationName,
				boolean isSwitcher,
				LANE currentLane) {
		
		this(id, stationName, isSwitcher, currentLane, null);
	
	}
	
	public StationInfo(int id,
				String stationName,
				boolean isSwitcher,
				LANE currentLane,
				LANE switchLane) {
		this.mId = id;
		this.mStationName = stationName;
		this.mIsSwitcher = isSwitcher;
		this.mCurrentLane = currentLane;
		this.mSwitchLane = switchLane;
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
    
    public int getId() {
		return mId;
	}

	public String getStationName() {
		return mStationName;
	}

	public boolean isSwitcher() {
		return mIsSwitcher;
	}

	public LANE getCurrentLane() {
		return mCurrentLane;
	}

	public LANE getSwitchLane() {
		return mSwitchLane;
	}


}
