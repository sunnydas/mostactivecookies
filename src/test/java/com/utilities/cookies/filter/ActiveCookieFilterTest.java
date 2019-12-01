package com.utilities.cookies.filter;

import com.utilities.cookies.exception.AppProcessingException;
import com.utilities.cookies.filter.matcher.CookieMatchHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActiveCookieFilterTest {

    private ActiveCookiesFilter activeCookiesFilter;

    @Before
    public void setup(){
        activeCookiesFilter = new ActiveCookiesFilter();
    }

    @Test
    public void testFilterHeaderLine() throws AppProcessingException {
        CookieMatchHolder cookieMatchHolder =
                activeCookiesFilter.findMatches("cookie,timestamp");
        Assert.assertTrue(cookieMatchHolder.getCookieName() == null);
        Assert.assertTrue(cookieMatchHolder.getDate() == null);
    }

    @Test
    public void testFilterEmptyLine() throws AppProcessingException {
        CookieMatchHolder cookieMatchHolder =
                activeCookiesFilter.findMatches("");
        Assert.assertTrue(cookieMatchHolder.getCookieName() == null);
        Assert.assertTrue(cookieMatchHolder.getDate() == null);
    }

    @Test
    public void testFilterValidLine() throws AppProcessingException {
        CookieMatchHolder cookieMatchHolder =
                activeCookiesFilter.findMatches("AtY0laUfhglK3lC7," +
                        "2019-12-09T14:19:00+00:00");
        Assert.assertTrue(cookieMatchHolder.getCookieName().equals("AtY0laUfhglK3lC7"));
        Assert.assertTrue(cookieMatchHolder.getDate().equals("2019-12-09"));
    }

    @Test
    public void testFilterInvalidDate() throws AppProcessingException {
        CookieMatchHolder cookieMatchHolder =
                activeCookiesFilter.findMatches("AtY0laUfhglK3lC7," +
                        "25-MAY-2019");
        Assert.assertTrue(cookieMatchHolder.getCookieName() == null);
        Assert.assertTrue(cookieMatchHolder.getDate() ==  null);
    }

}
