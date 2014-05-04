package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {

	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	private ArrayList<Crime> mCrimes;

	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
	}

	public static CrimeLab get(Context appContext) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(appContext);
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

}
