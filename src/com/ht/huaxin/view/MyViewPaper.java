package com.ht.huaxin.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ShareActionProvider;

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

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		boolean result = super.dispatchTouchEvent(ev);
//		Log.e("debug dispatchTouchEvent ", "result:" + result);
//		return result;
//	}
//  
//	public boolean onTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		boolean result = super.onTouchEvent(ev);
//		Log.e("debug",
//				"MyViewPaper  ontouch" + "  showing:" + shaderView.isShowing()
//						+ " result:" + result);
//		return result;
//		// if (shaderView.isShowing() == true) {
//		// Log.e("debug",
//		// "MyViewPaper  ontouch" + "  showing:"
//		// + shaderView.isShowing());
//		// return true;
//		// } else {
//		// return super.onTouchEvent(ev);
//		// }
//
//	}

	public ShaderView getShaderView() {
		return shaderView;
	}

	public void setShaderView(ShaderView s) {
		this.shaderView = s;
	}

}
