package com.ht.huaxin.image;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-12 下午04:39:25 file declare:
 */
public class DisplayUtil {
	private static Activity activity;

	public DisplayUtil(Activity ac) {
		this.activity = ac;
	}

	public static int getWidth() {

		Display display = activity.getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		Log.i("view", "widgth" + width);
		return width;
	}

	public static int getHeigth() {
		Display display = activity.getWindowManager().getDefaultDisplay();
		int height = display.getHeight();

		Log.i("view", "height:" + display.getHeight());
		return height;
	}
}
