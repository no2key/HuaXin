package com.ht.huaxin.database.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ht.huaxin.database.dao.interfaze.AlbumDao;
import com.ht.huaxin.entity.Album;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-18 下午04:00:24 file declare:
 */
public class AlbumDaoImpl extends BaseDaoImpl<Album, String> implements
		AlbumDao {

	public  AlbumDaoImpl(ConnectionSource connectionSource)
			throws SQLException {
		super(connectionSource, Album.class);
	}

	@Override
	public void batchInsert(List<Album> albums) {
		for (int i = 0; i < albums.size(); i++) {
			Album album = albums.get(i);
			try {
				create(album);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
