package com.utilities.cookies.active.cli;

import com.utilities.cookies.application.MostActiveCookiesSearchApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

public class MostActiveCLITest {

    private String cookieResourceName = "cookie.txt";
    private String emptyCookieResourceName = "cookie_empty.txt";
    private String denseCookieFileName = "cookie_dense.txt";

    private String cookieFilename;

    private String emptyCookieFileName;

    private String alternateCookieFile;

    @Before
    public void setup(){
        URL url = getClass().getClassLoader().getResource(cookieResourceName);
        Path path = new File(url.getPath()).toPath();
        cookieFilename = path.toAbsolutePath().toString();
        url = getClass().getClassLoader().getResource(emptyCookieResourceName);
        path = new File(url.getPath()).toPath();
        emptyCookieFileName = path.toAbsolutePath().toString();
        url = getClass().getClassLoader().getResource(denseCookieFileName);
        path = new File(url.getPath()).toPath();
        alternateCookieFile = path.toAbsolutePath().toString();
    }

    @Test
    public void testMissingArguments(){
        MostActiveCookieCLI.main(null);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.FAILED_EXEC_NO_ARGS_SPECIFIED));
    }

    @Test
    public void testIncorrectArgumentsNonExistentInputFile(){
        String[] args = new String[]{"nonexistent_file","-d","2018-12-09"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.FAILED_EXEC_INVALID_FILE));
    }

    @Test
    public void testIncorrectArgumentsInvalidDateString(){
        String[] args = new String[]{cookieFilename,"-d","junk2018-12-09"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.FAILED_EXEC_INVALID_DATE));
    }

    @Test
    public void testIncorrectArgumentsInvalidDateFormat(){
        String[] args = new String[]{cookieFilename,"-d","20-MAY-2019"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.FAILED_EXEC_INVALID_DATE));
    }

    @Test
    public void testCookieFoundSingleResult(){
        String[] args = new String[]{cookieFilename,"-d","2018-12-09"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.SUCCESSFUL_EXEC));
        Set<String> testResults = MostActiveCookieCLI.getMostActiveCookiesSearchApplication().
                getTestResults();
        Assert.assertTrue(testResults.size() == 1);
        Assert.assertTrue(testResults.contains("AtY0laUfhglK3lC7"));
        Assert.assertTrue(MostActiveCookieCLI.getMostActiveCookiesSearchApplication().getMaxCount() == 2);
    }

    @Test
    public void testCookieFoundMultipleResults(){
        String[] args = new String[]{cookieFilename,"-d","2018-12-08"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.SUCCESSFUL_EXEC));
        Set<String> testResults = MostActiveCookieCLI.getMostActiveCookiesSearchApplication().
                getTestResults();
        Assert.assertTrue(testResults.size() == 3);
        Assert.assertTrue(testResults.contains("SAZuXPGUrfbcn5UA"));
        Assert.assertTrue(testResults.contains("4sMM2LxV07bPJzwf"));
        Assert.assertTrue(testResults.contains("fbcn5UAVanZf6UtG"));
        Assert.assertTrue(MostActiveCookieCLI.getMostActiveCookiesSearchApplication().getMaxCount() == 1);
    }

    @Test
    public void testCookieNotFound(){
        String[] args = new String[]{cookieFilename,"-d","2019-12-09"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.SUCCESSFUL_EXEC));
        Set<String> testResults = MostActiveCookieCLI.getMostActiveCookiesSearchApplication().
                getTestResults();
        Assert.assertTrue(testResults.isEmpty());
    }


    @Test
    public void testCookieNotFoundEmptyCookieFile(){
        String[] args = new String[]{emptyCookieFileName,"-d","2019-12-09"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.SUCCESSFUL_EXEC));
        Set<String> testResults = MostActiveCookieCLI.getMostActiveCookiesSearchApplication().
                getTestResults();
        Assert.assertTrue(testResults.isEmpty());
    }

    @Test
    public void testCookieFoundEndOfFile(){
        String[] args = new String[]{cookieFilename,"-d","2010-11-07"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.SUCCESSFUL_EXEC));
        Set<String> testResults = MostActiveCookieCLI.getMostActiveCookiesSearchApplication().
                getTestResults();
        Assert.assertTrue(testResults.size() == 1);
        Assert.assertTrue(testResults.contains("4sMM2LxREXbPJzwf"));
    }

    @Test
    public void testCookieFoundForDenseCookieFile(){
        String[] args = new String[]{alternateCookieFile,"-d","2018-12-09"};
        MostActiveCookieCLI.main(args);
        Assert.assertTrue(MostActiveCookieCLI.getRunStatus().
                equals(RunStatus.SUCCESSFUL_EXEC));
        MostActiveCookiesSearchApplication mostActiveCookiesSearchApplication = MostActiveCookieCLI.getMostActiveCookiesSearchApplication();
        Set<String> testResults = mostActiveCookiesSearchApplication.getTestResults();
        Assert.assertTrue(testResults.size() == 1);
        Assert.assertTrue(testResults.contains("AtY0laUfhglK3lC7"));
        Assert.assertTrue(mostActiveCookiesSearchApplication.getMaxCount() == 8);
    }

}
