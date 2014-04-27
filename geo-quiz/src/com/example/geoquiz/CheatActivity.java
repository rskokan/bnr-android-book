package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	
	private void setAnswerShownResult(boolean answerShown) {
		Intent data = new Intent();
		data.putExtra(QuizActivity.EXTRA_ANSWER_SHOWN, answerShown);
		setResult(RESULT_OK, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_ANSWER_IS_TRUE, false);
		setAnswerShownResult(false);
		
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAnswerShownResult(true);
				
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
			}
		});
	}

}
