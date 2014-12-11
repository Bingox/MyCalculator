package com.test.mycalculator;

import com.test.drawtree.DrawTree;
import com.test.util.EvalExpression;
import com.test.view.KeyboardView;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MyCalculator extends ActionBarActivity {

	public static final char MINUS = '\u2212', TIMES = '\u00d7',
			DIV = '\u00f7', SQRT = '\u221a', PI = '\u03c0',
			UP_ARROW = '\u21e7', DN_ARROW = '\u21e9', ARROW = '\u21f3';

	private TextView result;
	private EditText input;

	private KeyboardView digits;

	/*
	 * private static final char[][] ALPHA = { {'q', 'w', '=', ',', ';', SQRT,
	 * '!', '\''}, {'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'}, {'a', 's', 'd',
	 * 'f', 'g', 'h', 'j', 'k'}, {'z', 'x', 'c', 'v', 'b', 'n', 'm', 'l'}, };
	 */

	private static final char[][] DIGITS = {
			{ '7', '8', '9', '%', '^', ARROW },
			{ '4', '5', '6', '(', ')', 'C' },
			{ '1', '2', '3', TIMES, DIV, 'E' },
			{ '0', '0', '.', '+', MINUS, 'E' }, };

	private static final char[][] DIGITS2 = {
			{ '0', '.', '+', MINUS, TIMES, DIV, '^', '(', ')', 'C' },
			{ '1', '2', '3', '4', '5', '6', '7', '8', '9', 'E' }, };

	public static final String INPUT_STATE = "INPUT";

	public static final String RESULT_STATE = "RESULT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		internalConfigChange(getResources().getConfiguration());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void internalConfigChange(Configuration config) {
		setContentView(R.layout.activity_my_calculator);

		final boolean isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE;

		digits = (KeyboardView) findViewById(R.id.digits);
		if (isLandscape) {
			digits.init(DIGITS2, false, true);
		} else {
			digits.init(DIGITS, true, true);
		}

		result = (TextView) findViewById(R.id.result);
		input = (EditText) findViewById(R.id.input);
		input.setInputType(0);
		input.requestFocus();
	}

	private StringBuilder oneChar = new StringBuilder(" ");

	public void onKey(char key) {
		if (key == 'E') {
			onEnter();
		} else if (key == 'C') {
			onBackspace();
		} else if (key == ARROW) {
			onArrow();
		} else {
			int cursor = input.getSelectionEnd();
			oneChar.setCharAt(0, key);
			input.getText().insert(cursor, oneChar);
		}
	}

	private static final KeyEvent KEY_DEL = new KeyEvent(0,
			KeyEvent.KEYCODE_DEL);

	private void onEnter() {
		String expr = input.getText().toString();
		if (!expr.equals("")) {
			Double res = EvalExpression.evaluate(expr);
			result.setText((res == null) ? "null" : res.toString());
		} else {
			result.setText("");
		}
	}

	private void onBackspace() {
		input.dispatchKeyEvent(KEY_DEL);
	}

	private void onArrow() {
		Intent tree = new Intent(this, DrawTree.class);
		Bundle bundle = new Bundle();
		bundle.putString(INPUT_STATE, input.getText().toString());
		bundle.putString(RESULT_STATE, result.getText().toString());
		tree.putExtras(bundle);
		this.startActivityForResult(tree, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			Bundle bundle = data.getExtras();
			String s = bundle.getString(INPUT_STATE);
			input.setText(s);
			input.setSelection(s.length());
			result.setText(bundle.getString(RESULT_STATE));
			break;
		default:
			break;

		}
	}
}
