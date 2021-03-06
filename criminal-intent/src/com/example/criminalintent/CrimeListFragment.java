package com.example.criminalintent;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;
	private boolean mSubtitleVisible;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		getActivity().setTitle(R.string.crimes_title);
		setHasOptionsMenu(true);

		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);
		mSubtitleVisible = false;
		setRetainInstance(true);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = super.onCreateView(inflater, container, savedInstanceState);
		if (mSubtitleVisible) {
			getActivity().getActionBar().setSubtitle(R.string.subtitle);
		}

		ListView listView = (ListView) v.findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.crime_list_item_context, menu);
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.menu_item_delete_crime:
					CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
					CrimeLab lab = CrimeLab.get(getActivity());
					for (int i = adapter.getCount() - 1; i >= 0; i--) {
						if (getListView().isItemChecked(i)) {
							lab.deleteCrime(adapter.getItem(i));
						}
					}
					mode.finish();
					adapter.notifyDataSetChanged();
					return true;
				default:
					return false;
				}
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// TODO Auto-generated method stub

			}
		});

		return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);
		Log.d(TAG, crime.getTitle() + " was clicked");
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.ARGS_CRIME_ID, crime.getId());
		startActivity(i);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
	}

	private class CrimeAdapter extends ArrayAdapter<Crime> {
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);
			}

			Crime crime = getItem(position);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(crime.getTitle());

			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(crime.getDate().toString());

			CheckBox solvedCheckBox = (CheckBox) convertView
					.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(crime.isSolved());

			return convertView;
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), CrimePagerActivity.class);
			i.putExtra(CrimeFragment.ARGS_CRIME_ID, crime.getId());
			startActivity(i);
			// startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				mSubtitleVisible = true;
			} else {
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
