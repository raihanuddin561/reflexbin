package com.reflexbin.reflexbin_api.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {

    public static ZonedDateTime getCurrentDateTime() {
        ZoneId zoneId = ZoneId.of("UTC+06:00");
        return ZonedDateTime.now(zoneId);
    }
}
