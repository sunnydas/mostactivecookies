package com.utilities.cookies.application;

import com.utilities.cookies.active.cli.util.DateUtils;
import com.utilities.cookies.exception.AppProcessingException;
import com.utilities.cookies.filter.matcher.CookieFilter;
import com.utilities.cookies.filter.matcher.CookieMatchHolder;
import com.utilities.cookies.parser.navigation.TextFileIterator;

import java.util.*;

public class MostActiveCookiesSearchApplication {

    private Map<String,Long> activeCookieTrackerMap;

    private CookieFilter cookieFilter;

    private TextFileIterator textFileIterator;

    public long getMaxCount() {
        return maxCount;
    }

    private long maxCount = 0;

    public Set<String> getTestResults() {
        return testResults;
    }

    private Set<String> testResults = new HashSet<>();

    public MostActiveCookiesSearchApplication(CookieFilter cookieFilter, TextFileIterator textFileIterator) {
        this.cookieFilter = cookieFilter;
        this.textFileIterator = textFileIterator;
        this.activeCookieTrackerMap = new LinkedHashMap<>();
    }

    public void process(String day) throws AppProcessingException {
        if(day != null){
            populateCookiesCountForDay(day);
            printMostActiveCookies();
        }
    }


    private void printMostActiveCookies(){
        if(!activeCookieTrackerMap.isEmpty() && maxCount > 0){
            Iterator<Map.Entry<String,Long>> iterator = activeCookieTrackerMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,Long> entry = iterator.next();
                if(entry.getValue() == maxCount){
                    String cookieName = entry.getKey();
                    testResults.add(cookieName);
                    System.out.println(cookieName);
                }
            }
        }
    }

    private void populateCookiesCountForDay(String inputDay) throws AppProcessingException {
        while (textFileIterator.hasNext()) {
            String line = textFileIterator.next();
            CookieMatchHolder cookieMatchHolder = cookieFilter.findMatches(line);
            if (cookieMatchHolder != null) {
                String cookieDate = cookieMatchHolder.getDate();
                if(cookieDate != null && cookieDate.equals(inputDay)){
                    upsertCountForCookie(cookieMatchHolder.getCookieName());
                }else if(DateUtils.isOlder(cookieDate,inputDay)){
                    //No need to check further, since all dates will be older than
                    // the current date.
                    textFileIterator.stop();
                    break;
                }
            }
        }
    }

    private void upsertCountForCookie(String cookieName){
        long curCount = 0L;
        if(activeCookieTrackerMap.containsKey(cookieName)){
             curCount = activeCookieTrackerMap.get(cookieName);
            activeCookieTrackerMap.put(cookieName,++curCount);
        }else{
            curCount = 1;
            activeCookieTrackerMap.put(cookieName,curCount);
        }
        updateMaxCount(curCount);
    }

    private void updateMaxCount(long currentCount){
        if(currentCount > maxCount){
            maxCount = currentCount;
        }
    }

}
