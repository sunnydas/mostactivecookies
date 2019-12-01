package com.utilities.cookies.parser.factory;

import com.utilities.cookies.parser.CookieTextFileParser;
import com.utilities.cookies.parser.navigation.TextFileIterator;

public class Parserfactory {

    public static TextFileIterator getTextFileIterator(String fileName){
        return new CookieTextFileParser(fileName);
    }

}
