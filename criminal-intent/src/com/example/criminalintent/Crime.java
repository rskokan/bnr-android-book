package com.example.criminalintent;

import java.util.UUID;

public class Crime {

	private UUID mId;
	private String mTitle;

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public UUID getId() {
		return mId;
	}

}