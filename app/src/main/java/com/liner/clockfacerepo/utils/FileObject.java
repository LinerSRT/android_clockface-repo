package com.liner.clockfacerepo.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

public class FileObject {
    public String mime;
    public long size;
    public String humanSize;
    public String name;
    public String path;
    public byte[] bytes;


    public String toHumanSize(long bytes) {
        if (-1000 < bytes && bytes < 1000)
            return bytes + " B";
        CharacterIterator characterIterator = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            characterIterator.next();
        }
        return String.format(Locale.getDefault(), "%.1f %cB", bytes / 1000.0, characterIterator.current());
    }
}
