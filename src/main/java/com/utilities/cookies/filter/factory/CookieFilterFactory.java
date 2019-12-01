package com.utilities.cookies.filter.factory;

import com.utilities.cookies.filter.ActiveCookiesFilter;
import com.utilities.cookies.filter.matcher.CookieFilter;

public class CookieFilterFactory {


    public static CookieFilter getCookieFilterFactory(){
        return new ActiveCookiesFilter();
    }

}
