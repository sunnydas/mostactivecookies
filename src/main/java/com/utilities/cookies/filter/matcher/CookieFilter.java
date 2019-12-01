package com.utilities.cookies.filter.matcher;

import com.utilities.cookies.exception.AppProcessingException;

public interface CookieFilter {

    public CookieMatchHolder findMatches(String singleLine) throws AppProcessingException;
}
