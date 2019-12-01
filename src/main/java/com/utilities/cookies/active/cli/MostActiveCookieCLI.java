package com.utilities.cookies.active.cli;

import com.utilities.cookies.active.cli.util.DateUtils;
import com.utilities.cookies.application.MostActiveCookiesSearchApplication;
import com.utilities.cookies.exception.AppProcessingException;
import com.utilities.cookies.filter.factory.CookieFilterFactory;
import com.utilities.cookies.filter.matcher.CookieFilter;
import com.utilities.cookies.parser.factory.Parserfactory;
import com.utilities.cookies.parser.navigation.TextFileIterator;

import java.nio.file.Files;
import java.nio.file.Paths;

public class MostActiveCookieCLI {


    public static void printHelp(){
        System.out.println("Usage: ./most_active_cookie <FILE_NAME> -d <DAY>");
        System.out.println("Example: ./most_active_cookie cookie.txt -d 2018-12-08");
    }

    public static RunStatus getRunStatus() {
        return runStatus;
    }

    private static RunStatus runStatus;

    public static MostActiveCookiesSearchApplication getMostActiveCookiesSearchApplication() {
        return mostActiveCookiesSearchApplication;
    }

    private static MostActiveCookiesSearchApplication mostActiveCookiesSearchApplication;

    public static void processRequest(String fileName,String day){
        boolean isValidated = validate(fileName,day);
        if(isValidated){
            try {
                TextFileIterator textFileIterator = Parserfactory.getTextFileIterator(fileName);
                CookieFilter cookieFilter = CookieFilterFactory.getCookieFilterFactory();
                mostActiveCookiesSearchApplication = new MostActiveCookiesSearchApplication(cookieFilter,
                        textFileIterator);
                mostActiveCookiesSearchApplication.process(day);
                runStatus = RunStatus.SUCCESSFUL_EXEC;
            } catch (AppProcessingException e) {
                System.err.println("Exception during processing of request, error code " + e.getAppExceptionCode()
                        + " error message = " + e.getMessage());
            }
        }
    }

    public static boolean validate(String fileName,String day){
        if(!Files.exists(Paths.get(fileName))){
            System.err.println("File = " + fileName + " does not exist");
            handleIncorrectUserParameters(RunStatus.FAILED_EXEC_INVALID_FILE);
            return false;
        }
        if(!DateUtils.isValid(day)){
            System.err.println("Day = " + day + " is invalid");
            handleIncorrectUserParameters(RunStatus.FAILED_EXEC_INVALID_DATE);
            return false;
        }
        return true;
    }

    private static void handleIncorrectUserParameters(RunStatus status) {
        runStatus = status;
        printHelp();
        return;
    }

    public static void main(String[] args) {
        if(args != null && args.length == 3){
            String fileName = args[0];
            String day = args[2];
            processRequest(fileName,day);
        }else{
            runStatus = RunStatus.FAILED_EXEC_NO_ARGS_SPECIFIED;
            printHelp();
        }
    }

}
