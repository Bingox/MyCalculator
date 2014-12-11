package com.test.drawtree;

import java.util.ArrayList;

import com.test.util.EvalExpression;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class TreeView extends View {

	private Paint mPaint = null;
	private BiTree bt = null;

	public TreeView(Context context) {
		super(context);
	}

	public TreeView(Context context, String expr) {
		super(context);
		mPaint = new Paint();
		ArrayList<Object> postExpr = EvalExpression.transExpression(expr);
		bt = EvalExpression.volidate(postExpr) ? BiTree.createBiTree(postExpr) : null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setTextSize(20);
		if (bt == null) {
			canvas.drawText("Error!", 0, 20, mPaint);
		} else {
			canvas.drawText("Expression Tree:", 0, 20, mPaint);
			drawTree(canvas, bt, 220, 20, 3);
		}
	}

	private void drawTree(Canvas canvas, BiTree bt, int x, int y, int level) {
		level++;
		if (bt != null) {
			String s = bt.data.toString();
			canvas.drawText(s, x - s.length()*5, y + 20, mPaint);
			if (bt.left != null) {
				canvas.drawLine(x, y + 20, (float) (x-300/level), y+10*level, mPaint);
				drawTree(canvas,bt.left,(int) (x-300/level),y+10*level,level);
			}
			if (bt.right != null) {
				canvas.drawLine(x, y + 20, (float) (x+300/level), y+10*level, mPaint);
				drawTree(canvas,bt.right,(int) (x+300/level),y+10*level,level);
			}
		}
	}
	
}
