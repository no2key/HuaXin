package com.ht.huaxin.utils;

public class Constants {
	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}

	public static final String URL_PREFIX = "http://121.199.16.173/";
	public static final String ALBUM_URL_PREFIX = URL_PREFIX + "albums";
	public static final String PICTURE_URL_PREFIX = URL_PREFIX + "album/";
	public static final String Image_URL_PREFIX = URL_PREFIX + "static/";
	public static final String Image_URL_BACKFIX = ".jpg";
	public static final String Image_PNG_URL_BACKFIX = ".png";

	public static final String EXTRA_Album_id = "albumID";
	public static final String EXTRA_Album_title = "albumTitle";
}
