package com.utilities.cookies.parser;

import com.utilities.cookies.exception.AppExceptionCode;
import com.utilities.cookies.exception.AppProcessingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class CookieTextFileParserTest {

    private String cookieResourceName = "cookie.txt";

    @Test
    public void testParsingForValidFile() throws AppProcessingException {
        CookieTextFileParser cookieTextFileParser = getParser();
        Assert.assertTrue(cookieTextFileParser.hasNext());
        String line = null;
        while(cookieTextFileParser.hasNext()
                && ((line = cookieTextFileParser.next())!= null)){
            Assert.assertTrue(line != null);
            Assert.assertTrue(!line.isEmpty());
        }
        Assert.assertFalse(cookieTextFileParser.hasNext());
    }

    @Test
    public void testParsingWithStopIterationFunctionality() throws AppProcessingException {
        CookieTextFileParser cookieTextFileParser = getParser();
        Assert.assertTrue(cookieTextFileParser.hasNext());
        String line = null;
        int i = 0;
        while(cookieTextFileParser.hasNext()
                && ((line = cookieTextFileParser.next())!= null)){
            Assert.assertTrue(line != null);
            Assert.assertTrue(!line.isEmpty());
            if(i == 2){
                cookieTextFileParser.stop();
                Assert.assertFalse(cookieTextFileParser.hasNext());
                break;
            }
            i++;
        }
        try{
            cookieTextFileParser.next();
        }catch (AppProcessingException e){
            Assert.assertTrue(
                    e.getAppExceptionCode().equals(AppExceptionCode.ERROR_COOKIE_FILE_READ));
            Assert.assertTrue(e.getCause() instanceof IOException);
        }
    }

    private CookieTextFileParser getParser() {
        URL url = getClass().getClassLoader().getResource(cookieResourceName);
        Path path = new File(url.getPath()).toPath();
        String cookieFilename = path.toAbsolutePath().toString();
        CookieTextFileParser cookieTextFileParser = new CookieTextFileParser(cookieFilename);
        return cookieTextFileParser;
    }

}
