package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	private static final String TAG = "CheatActivity";
	private boolean mAnswerIsTrue;
	private boolean mAnswerShown;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	private static final String KEY_ANSWER_SHOWN = "key_answer_shown";

	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.putExtra(QuizActivity.EXTRA_ANSWER_SHOWN, mAnswerShown);
		setResult(RESULT_OK, data);
		Log.d(TAG, "onBackPressed() called");
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);

		mAnswerIsTrue = getIntent().getBooleanExtra(
				QuizActivity.EXTRA_ANSWER_IS_TRUE, false);

		mAnswerShown = false;
		if (savedInstanceState != null) {
			mAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN,
					false);
		}

		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mAnswerShown = true;

				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
			}
		});
		
		String apiLevel = "API Level " + Build.VERSION.SDK_INT;
		((TextView) findViewById(R.id.apiVersionTextView)).setText(apiLevel);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_ANSWER_SHOWN, mAnswerShown);
	}

}
