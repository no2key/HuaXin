package com.ht.huaxin.database.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ht.huaxin.database.dao.interfaze.AlbumDao;
import com.ht.huaxin.entity.Album;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmDateBaseHelper extends SQLiteOpenHelper {

	private static AlbumDao albumDao;

	public OrmDateBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		initScheme();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("debug", "onCreate");

	}

	public void initScheme() {
		ConnectionSource connectionSource = new AndroidConnectionSource(this);
		try {
			TableUtils.createTableIfNotExists(connectionSource, Album.class);
			albumDao = DaoManager.createDao(connectionSource, Album.class);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.e("debug", "onCreate");
	}

	public static AlbumDao getAlbumDao() {
		return albumDao;
	}

	public static void setAlbumDao(AlbumDao albumDao) {
		OrmDateBaseHelper.albumDao = albumDao;
	}

}
