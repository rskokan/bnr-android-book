package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {

	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";

	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;

	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			Log.e(TAG, "Error loading crime:", e);
			mCrimes = new ArrayList<Crime>();
		}
	}

	public static CrimeLab get(Context appContext) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(appContext);
			sCrimeLab.mSerializer = new CriminalIntentJSONSerializer(
					appContext, FILENAME);
		}

		return sCrimeLab;
	}

	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}

	public Crime getCrime(UUID id) {
		for (Crime crime : mCrimes) {
			if (crime.getId().equals(id)) {
				return crime;
			}
		}

		return null;
	}

	public void addCrime(Crime crime) {
		mCrimes.add(crime);
	}

	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "Crimes saved");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}

}
