package com.ht.huaxin.database.dao.interfaze;

import java.util.List;

import com.ht.huaxin.entity.Album;
import com.j256.ormlite.dao.Dao;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-18 下午03:59:37 file declare:
 */
public interface AlbumDao extends Dao<Album, String> {
	public void batchInsert(List<Album> albums);

}
