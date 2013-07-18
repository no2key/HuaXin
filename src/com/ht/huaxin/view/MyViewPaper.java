package com.ht.huaxin.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-16 下午04:52:42 file declare:
 */
public class MyViewPaper extends ViewPager {

	private ShaderView shaderView;

	public MyViewPaper(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (shaderView.isShowing()) {
			return false;
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.e("debug",
				"MyScrollView  ontouch" + "  showing:" + shaderView.isShowing());
		if (shaderView.isShowing() == true) {
			return true;
		} else {
			return super.onTouchEvent(ev);
		}
		// return true;
	}

	public ShaderView getShaderView() {
		return shaderView;
	}

	public void setShaderView(ShaderView s) {
		this.shaderView = s;
	}
	
	

}
