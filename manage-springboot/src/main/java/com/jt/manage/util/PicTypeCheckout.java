package com.jt.manage.util;

import java.util.ArrayList;
import java.util.List;

public class PicTypeCheckout {
	public static List<String> contentTypes;
	static{
		contentTypes=new ArrayList<String>();
		contentTypes.add("bmp");
		contentTypes.add("gif");
		contentTypes.add("jpeg");
		contentTypes.add("jpg");
		contentTypes.add("png");
	}
}
