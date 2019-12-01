package com.utilities.cookies.parser;

import com.utilities.cookies.exception.AppExceptionCode;
import com.utilities.cookies.exception.AppProcessingException;
import com.utilities.cookies.parser.navigation.TextFileIterator;

import java.io.*;
import java.util.logging.Logger;

public class CookieTextFileParser implements TextFileIterator {

    private static Logger logger = Logger.getLogger(CookieTextFileParser.class.getName());

    private String cookieFileLocation;

    private File cookieFileHandle;

    private FileReader cookieFileReader;

    private BufferedReader bufferedCookieFileReader;

    private boolean parsingStarted;

    private String currentLine;

    public CookieTextFileParser(String cookieFileLocation) {
        this.cookieFileLocation = cookieFileLocation;
    }

    public String next()throws AppProcessingException {
        try {
            if (!parsingStarted) {
                init();
            }
            currentLine = bufferedCookieFileReader.readLine();
            if(currentLine == null){
                closeReaders(bufferedCookieFileReader,cookieFileReader);
            }
        } catch (Exception e){
            currentLine = null;
            throw new AppProcessingException(e,AppExceptionCode.ERROR_COOKIE_FILE_READ);
        } finally {
            if(currentLine == null){
                closeReaders(bufferedCookieFileReader,cookieFileReader);
            }
        }
        return currentLine;
    }

    public boolean hasNext(){
        return (!parsingStarted) || (parsingStarted && (currentLine != null));
    }

    private void init() throws FileNotFoundException {
        cookieFileHandle = new File(cookieFileLocation);
        cookieFileReader = new FileReader(cookieFileHandle);
        bufferedCookieFileReader = new BufferedReader(cookieFileReader);
        parsingStarted = true;
    }

    public void stop(){
        currentLine = null;
        closeReaders(bufferedCookieFileReader,cookieFileReader);
    }

    private void closeReaders(Reader... readers){
        if(readers != null){
            for(Reader reader : readers){
                closeReaderSilently(reader);
            }
        }
    }

    private void closeReaderSilently(Reader reader){
        try {
            if(reader != null) {
                reader.close();
            }
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
    }
}