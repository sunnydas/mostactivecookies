package com.utilities.cookies.filter;

import com.utilities.cookies.config.AppConfig;
import com.utilities.cookies.exception.AppProcessingException;
import com.utilities.cookies.filter.matcher.CookieFilter;
import com.utilities.cookies.filter.matcher.CookieMatchHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActiveCookiesFilter implements CookieFilter {

    private String regex;

    private boolean initialized;

    private static final String SEARCH_PATTERN_PROP_KEY="searchPattern";

    private Pattern pattern;

    private void init() throws AppProcessingException {
        regex = AppConfig.getProperty(SEARCH_PATTERN_PROP_KEY);
        pattern = Pattern.compile(regex);
        initialized = true;
    }


    @Override
    public CookieMatchHolder findMatches(String singleLine)
            throws AppProcessingException {
        CookieMatchHolder cookieMatchHolder = null;
        if(singleLine != null){
            if(!initialized){
                init();
            }
            cookieMatchHolder = new CookieMatchHolder();
            Matcher matcher = pattern.matcher(singleLine);
            if(matcher.find()
                    && matcher.groupCount() == 2){
                cookieMatchHolder.setCookieName(matcher.group(1));
                cookieMatchHolder.setDate(matcher.group(2));
            }
        }
        return cookieMatchHolder;
    }

}
