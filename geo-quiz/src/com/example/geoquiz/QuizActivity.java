package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	private Button mCheatButton;
	private boolean mIsCheater;

	private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String KEY_IS_CHEATER = "is_cheater";
	public static final String EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown";

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false) };
	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);

		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
		}

		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				i.putExtra(EXTRA_ANSWER_IS_TRUE,
						mQuestionBank[mCurrentIndex].isTrueQuestion());
				startActivityForResult(i, 0);
			}
		});

		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		updateQuestion();
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});

		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextQuestion();
			}
		});

		mPrevButton = (ImageButton) findViewById(R.id.prev_button);
		mPrevButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				previousQuestion();
			}
		});
	}
	
	private void previousQuestion() {
		mCurrentIndex--;
		if (mCurrentIndex < 0) {
			mCurrentIndex = mQuestionBank.length - 1;
		}
		updateQuestion();
		mIsCheater = false;
	}

	private void nextQuestion() {
		mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
		updateQuestion();
		mIsCheater = false;
	}

	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId;

		if (mIsCheater) {
			messageResId = R.string.judgement_toast;
		} else if (userPressedTrue == answerIsTrue) {
			messageResId = R.string.correct_toast;
		} else {
			messageResId = R.string.incorrect_toast;
		}

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState(Bundle) called");
		outState.putInt(KEY_INDEX, mCurrentIndex);
		outState.putBoolean(KEY_IS_CHEATER, mIsCheater);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}

		mIsCheater = data.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
	}

}
