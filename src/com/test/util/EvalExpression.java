package com.test.util;

import java.util.ArrayList;
import java.util.Stack;
import android.util.Log;
import com.test.mycalculator.MyCalculator;

public class EvalExpression {

	public static ArrayList<Object> transExpression(String expr) {
		if (expr == null || expr.equals("")) {
			Log.e("EvalExpression","The expression is null or empty!");
			return null;
		} 
		ArrayList<Object> postExpr = new ArrayList<Object>();
		Stack<Character> opStack = new Stack<Character>();
		String tmp = "";
		char c;
		expr += "#";
		for (int i=0; i<expr.length(); i++) {
			c = expr.charAt(i);
			if (Character.isDigit(c)) {
				while (Character.isDigit(c) || c == '.') {
					tmp += c;
					c = expr.charAt(++i);
				}
				postExpr.add(Double.parseDouble(tmp));
				tmp = "";
			}
			switch (c) {
			case '(':
				if (expr.charAt(i+1) == '+' || expr.charAt(i+1) == MyCalculator.MINUS) {
					postExpr.add(0.0);
				}
				opStack.push('(');
				break;
			case ')':
				while (!opStack.isEmpty() && opStack.peek() != '(') {	
					postExpr.add(opStack.pop());
				}
				if (opStack.isEmpty()) {
					Log.e("EvalExpression","The bracket does not match!");
					return null;
				}
				opStack.pop();
				break;
			case ' ':
			case '#':
				break;
			default:
				if (postExpr.isEmpty() && opStack.isEmpty() && ('+' == c || MyCalculator.MINUS == c)) {
					postExpr.add(0.0);
				}
				while (!opStack.isEmpty() && opStack.peek() != '(' 
						&& comPriority(c, opStack.peek())) {
					postExpr.add(opStack.pop());
				}
				opStack.push(c);
				break;
			}
		}
		while (!opStack.isEmpty()) {
			if (opStack.peek() == '(') {
				Log.e("EvalExpression","The bracket does not match!");
				return null;
			}
			postExpr.add(opStack.pop());
		}
		return postExpr;
	}
	
	private static boolean comPriority(char curr, char peek) {
		if ('^' == peek) {
			return true;
		} else if ('^' == curr) {
			return false;
		} else if (MyCalculator.TIMES == peek || MyCalculator.DIV == peek) {
			return true;
		} else if (MyCalculator.MINUS == curr || '+' == curr) {
			return true;
		}
		return false;
	}
	
	public static boolean volidate(ArrayList<Object> postExpr) {
		if (postExpr == null || postExpr.isEmpty()) {
			return false;
		}
		int cnt = 0;
		for (Object o : postExpr) {
			if (o instanceof Double) {
				cnt++;
			}
		}
		return ((2 * cnt - postExpr.size()) == 1 ? true : false);
	}
	
	public static Double evaluate(String Expr) {
		ArrayList<Object> postExpr = transExpression(Expr);
		if (!volidate(postExpr)) {
			Log.e("EvalExpression","Error: post expression is " + 
					(postExpr == null ? "null" : postExpr.toString()) + "!");
			return null;
		}
		
		Stack<Double> valStack = new Stack<Double>();
		double tmp = 0;
		for (Object o : postExpr) {
			if (o instanceof Double) {
				valStack.push((Double)o);
			} else {
				switch((Character)o) {
				case '+':
					tmp = valStack.pop();
					valStack.set(valStack.size()-1, valStack.peek() + tmp);
					break;
				case MyCalculator.MINUS:
					tmp = valStack.pop();
					valStack.set(valStack.size()-1, valStack.peek() - tmp);
					break;
				case MyCalculator.TIMES:
					tmp = valStack.pop();
					valStack.set(valStack.size()-1, valStack.peek() * tmp);
					break;
				case MyCalculator.DIV:
					if ((tmp = valStack.pop()) == 0) {
						Log.e("EvalExpression","The denominator is zero!");
						return null;
					} 
					valStack.set(valStack.size()-1, valStack.peek() / tmp);
					break;
				case '^':
					tmp = valStack.pop();
					if (tmp == 0 && valStack.peek().equals(0.0)) {
						return null;
					}
					valStack.set(valStack.size()-1, Math.pow(valStack.peek(), tmp));
					break;
				}
			}
		}
		return valStack.peek();
	}

}
