package com.thoughtworks;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thoughtworks.model.StationInfo;
import com.thoughtworks.utils.StationsDataUtils;

import java.util.ArrayList;

/**
 * Stations list adapter.
 */
public class StationsListAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;

	private final ArrayList<StationInfo> mStationsList;
	private final Context mContext;

	private LayoutInflater mInflater;

	private int mSourceID;
	private int mDestinationID;

	/**
	 * Instantiates a new Stations list adapter.
	 *
	 * @param context the context
	 */
	StationsListAdapter (Context context) {
		mContext = context;
		mStationsList = StationsDataUtils.getAllStations();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSourceID = -1;
		mDestinationID = -1;

	}

	@Override
	public int getCount() {
		return mStationsList.size();
	}

	@Override
	public StationInfo getItem(int position) {
		return mStationsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return mStationsList.get(position).isDummy() ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * Gets destination id.
	 *
	 * @return the destination id
	 */
	public int getDestinationID() {
		return mDestinationID;
	}

	/**
	 * Sets destination id.
	 *
	 * @param destinationID the destination id
	 */
	public void setDestinationID(int destinationID) {
		this.mDestinationID = destinationID;
		notifyDataSetChanged();
	}

	/**
	 * Gets source id.
	 *
	 * @return the source id
	 */
	public int getSourceID() {
		return mSourceID;
	}

	/**
	 * Sets source id.
	 *
	 * @param sourceID the source id
	 */
	public void setSourceID(int sourceID) {
		this.mSourceID = sourceID;
		notifyDataSetChanged();
	}

	/**
	 * Reset selections.
	 */
	public void resetSelections() {
		mSourceID = -1;
		mDestinationID = -1;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder;
		int rowType = getItemViewType(position);

		if (convertView == null) {
			holder = new ViewHolder();
//			switch (rowType) {
//				case TYPE_ITEM:
//					convertView = mInflater.inflate(R.layout.list_normal_item, null);
//					holder.itemNameTextView = (TextView) convertView.findViewById(R.id.txt_station);
//					holder.barView = convertView.findViewById(R.id.view_color_bar);
//					break;
//				case TYPE_SEPARATOR:
//					convertView = mInflater.inflate(R.layout.list_section_item, null);
//					holder.itemNameTextView = (TextView) convertView.findViewById(R.id.txt_title);
//					break;
//			}

			convertView = mInflater.inflate(R.layout.list_normal_item, null);
			holder.itemNameTextView = (TextView) convertView.findViewById(R.id.txt_station);
			holder.barView = convertView.findViewById(R.id.view_color_bar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.itemNameTextView.setText(getItem(position).getStationName());
		if (rowType == TYPE_ITEM) {
			setBarColor(holder.barView, getItem(position));
		} else {
			holder.barView.setVisibility(View.INVISIBLE);
		}
//		switch (rowType) {
//			case TYPE_ITEM:
//				setBarColor(holder.barView, getItem(position));
//				break;
//			case TYPE_SEPARATOR:
//				if (holder.barView != null) {
//					holder.barView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
//				}
//				break;
//		}
		deselectRow(holder.itemNameTextView);
		if (mSourceID > 0) {
			if (getItem(position).getId() == mSourceID) {
				selectSourceRow(holder.itemNameTextView);
			}
		}

		if (mDestinationID > 0) {
			if (getItem(position).getId() == mDestinationID) {
				selectDestinationRow(holder.itemNameTextView);
			}
		}
		return convertView;
	}

	private void selectSourceRow(View convertView) {
		convertView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark));
	}

	private void selectDestinationRow(View convertView) {
		convertView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark));
	}

	private void deselectRow(View convertView) {
		convertView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
	}


	/**
	 * Update the bar color by lane
	 * @param barView // color bar for particular view
	 * @param item // StationInfo item used to determine lane
	 */
	private void setBarColor(View barView, StationInfo item) {
		switch (item.getCurrentLane()) {
			case RED:
				barView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_light));
				break;
			case GREEN:
				barView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark));
				break;
			case BLUE:
				barView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_dark));
				break;
			case BLACK:
				barView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.black));
				break;
			case YELLOW:
				barView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
				break;
//			case ORANGE:
//				barView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_bright));
//				break;
		}
	}

	/**
	 * The type View holder.
	 */
	private static class ViewHolder {
		/**
		 * The Item name text view.
		 */
		public TextView itemNameTextView;
		/**
		 * The Bar view.
		 */
		public View barView;
	}

}
