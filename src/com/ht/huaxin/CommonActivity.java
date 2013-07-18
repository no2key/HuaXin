package com.ht.huaxin;

import com.ht.huaxin.database.dao.OrmDateBaseHelper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-18 下午04:06:05 file declare:
 */
public class CommonActivity extends Activity {

	private OrmDateBaseHelper ormDateBaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		UILApplication app = (UILApplication) getApplication();
		ormDateBaseHelper = app.getOrmDateBaseHelper();

	}

	public OrmDateBaseHelper getOrmDateBaseHelper() {
		return ormDateBaseHelper;
	}

}
