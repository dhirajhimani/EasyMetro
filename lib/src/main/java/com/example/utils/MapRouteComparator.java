package com.example.utils;

import com.example.model.MetroRoute;

import java.util.Comparator;


public class MapRouteComparator implements Comparator<MetroRoute> {
	
	private final boolean mCompareByTime;
	
	public MapRouteComparator (boolean compareByTime) {
		mCompareByTime = compareByTime;
	}
	

	@Override
	public int compare(MetroRoute metroRoute1, MetroRoute metroRoute2) {
		if (mCompareByTime) {
			// Sort by Time
			int compareTime = compareByTime(metroRoute1, metroRoute2);
			if (compareTime == 0) {
				return compareByRoute(metroRoute1, metroRoute2);
			} else {
				return compareTime;
			}
		} else {
			// Sort by Price
			int compareRoute = compareByRoute(metroRoute1, metroRoute2);
			if (compareRoute == 0) {
				return compareByTime(metroRoute1, metroRoute2);
			} else {
				 return compareRoute;
			}
		}
	}

	private int compareByTime(MetroRoute metroRoute1, MetroRoute metroRoute2) {
		if (metroRoute1.getTime() < metroRoute2.getTime()) {
			return -1;
		} else if (metroRoute1.getTime() > metroRoute2.getTime()) {
			return 1;
		} else {
			return 0;
		}
	}

	private int compareByRoute(MetroRoute metroRoute1, MetroRoute metroRoute2) {
		if (metroRoute1.getPrice() < metroRoute2.getPrice()) {
			return -1;
		} else if (metroRoute1.getPrice() > metroRoute2.getPrice()) {
			return 1;
		} else {
			return 0;
		}
	}

}
