package org.example;

public interface State {
    abstract String FormatToString(String filePath);
    abstract void StringToFormat(String text,String filePath);
}
