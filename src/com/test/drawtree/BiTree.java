package com.test.drawtree;

import java.util.ArrayList;
import java.util.Stack;

public class BiTree {
	public Object data;
	public BiTree left;
	public BiTree right;
	
	public BiTree() {
		super();
	}
	
	public BiTree(Object data) {
		super();
		this.data = data;
	}
	
	public void preOrderTrace(BiTree bt) {
		if (bt != null) {
			System.out.print(bt.data + " ");
			preOrderTrace(bt.left);
			preOrderTrace(bt.right );
		}
	}
	
	public void midOrderTrace(BiTree bt) {
		if (bt != null) {
			midOrderTrace(bt.left );
			System.out.print(bt.data + " ");
			midOrderTrace(bt.right );
		}
	}
	
	public void postOrderTrace(BiTree bt) {
		if (bt != null) {
			postOrderTrace(bt.left );
			postOrderTrace(bt.right );
			System.out.print(bt.data + " ");
		}
	}
	
	public static BiTree createBiTree(ArrayList<Object> postExpr) {
		Stack<BiTree> stk = new Stack<BiTree>();
		for(Object s : postExpr) {
			if (s instanceof Double) {
				stk.push(new BiTree(s));
			} else if (s instanceof Character) {
				if (!stk.isEmpty()) {
					BiTree subRoot = new BiTree(s);
					subRoot.right = stk.pop();
					subRoot.left = stk.pop();
					stk.push(subRoot);
				}
			}
		}
		return stk.isEmpty() ? null : stk.pop();
	}
}