package com.test.drawtree;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.content.Intent;

import com.test.mycalculator.MyCalculator;

public class DrawTree extends ActionBarActivity {
	
	Intent intent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String expr = bundle.getString(MyCalculator.INPUT_STATE);
		TreeView tv = new TreeView(this, expr);
		setContentView(tv);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.setResult(RESULT_OK, intent);
			this.finish();
			return true;
		}
		return false;
	}

	
}
