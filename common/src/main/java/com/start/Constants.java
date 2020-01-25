package com.start;

import java.awt.*;

public class Constants {

    public static final Long REDIS_CACHE_TIME_TEN_YEAR = 10 * 365 * 24 * 60 * 60L;

    public static final String HEADER_NAME_ACCESS_TOKEN = "Access-Token";
    public static final String HEADER_NAME_REFRESH_TOKEN = "Refresh-Token";
    public static final String HEADER_NAME_SECRET_TOKEN = "Secret-Token";

    // 生成头像时的背景色，字体颜色，字体大小，字体等信息
    public static String[] backgroundColor = {"#846363","#676d8d","#936976","#677b8d","#678d8a",
            "#558668","#413d45","#413a32","#815a4b","#b78f87"};
    public static Color frontColor = Color.WHITE;
    public static int frontSize = 70;
    public static String fontType = "PingFang SC";
    public static int avatarWidth = 250;
    public static int avatarHeight = 250;

}
