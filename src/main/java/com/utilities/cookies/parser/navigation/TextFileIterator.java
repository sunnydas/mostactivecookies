package com.utilities.cookies.parser.navigation;

import com.utilities.cookies.exception.AppProcessingException;

public interface TextFileIterator {

    public String next()throws AppProcessingException;

    public boolean hasNext();

    public void stop();

}
