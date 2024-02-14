package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Period period) {
        return DateUtil.format(period.start()) + " - " + DateUtil.format(period.end());
    }
}
