package com.thoughtworks;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.thoughtworks.model.MetroRoute;
import com.thoughtworks.model.StationInfo;
import com.thoughtworks.utils.MetroUtils;

/**
 * Metro route calculate activity.
 */
public class MetroRouteCalculateActivity extends AppCompatActivity
										implements AdapterView.OnItemClickListener,
												   View.OnClickListener {

	private TextView mSourceText;
	private TextView mDestinationText;
	private ListView mStationsList;

	private StationInfo mSource;
	private StationInfo mDestination;

	private StationsListAdapter mStationsListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metro_route_calculate);
		initViews();
		assignClickListeners();
		// Fill List with stations
		mStationsListAdapter = new StationsListAdapter(this);
		mStationsList.setAdapter(mStationsListAdapter);
	}

	private void assignClickListeners() {
		findViewById(R.id.btn_reset).setOnClickListener(this);
		findViewById(R.id.btn_calculate).setOnClickListener(this);
		mStationsList.setOnItemClickListener(this);
	}

	private void initViews() {
		mSourceText = (TextView)findViewById(R.id.txt_src);
		mDestinationText = (TextView)findViewById(R.id.txt_dest);
		mStationsList = (ListView) findViewById(R.id.list_stations);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		StationInfo stationInfo = mStationsListAdapter.getItem(position);
		if (stationInfo.isDummy()) {
			// Do nothing for section Item
			return;
		}
		if (mSource == null) {
			mSource = stationInfo;
			mStationsListAdapter.setSourceID(stationInfo.getId());
			mSourceText.setText(mSource.getStationName());
			return;
		}
		if (mSource.equals(stationInfo)) {
			// Show alert/toast , src and dst cannot be same
			showAlert("Error!", "Source and Destination cannot be same");
			return;
		} else {
			// we can set destinations multiple times for same source.
			mDestination = stationInfo;
			mStationsListAdapter.setDestinationID(stationInfo.getId());
			mDestinationText.setText(mDestination.getStationName());
			return;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_reset :
				mSource = null;
				mDestination = null;
				mSourceText.setText("");
				mDestinationText.setText("");
				mStationsListAdapter.resetSelections();
				break;
			case R.id.btn_calculate:
				if (mSource != null && mDestination != null) {
					MetroRoute metroRoute = MetroUtils.getBestPath(mSource, mDestination);
					if(metroRoute != null) {
						// show MetroRoute
						showAlert("Path", metroRoute.getRouteInfo());
					} else {
						// This would be case when src=dest , which we have already handled,
						// but for testing we have implemented it.

						// show alert invalid src and dest
						showAlert("Error!", "Invalid Source and Destination");
					}
				}
				break;
		}
	}

	/**
	 * Show alert.
	 *
	 * @param title   the title
	 * @param message the message
	 */
	public void showAlert(String title, String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(getString(android.R.string.ok), null);

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
}
