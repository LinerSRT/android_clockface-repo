package com.liner.clockfacerepo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Files {

    public static long getFileSize(File file) {
        return Objects.requireNonNull(getFile(file)).size;
    }

    public static long getFilesSize(File[] files) {
        long size = 0L;
        for (File file : files)
            size += getFileSize(file);
        return size;
    }

    @Nullable
    public static FileObject getFile(File file) {
        if (!file.exists() || !file.canRead())
            return null;
        FileObject fileObject = new FileObject();
        fileObject.name = file.getName();
        fileObject.path = file.getAbsolutePath();
        fileObject.size = file.length();
        fileObject.mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileObject.name.substring(fileObject.name.lastIndexOf(".")));
        byte[] bytes = new byte[(int) file.length()];
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file))) {
            stream.read(bytes, 0, bytes.length);
            fileObject.bytes = bytes;
        } catch (IOException e) {
            fileObject.bytes = new byte[0];
        }
        fileObject.humanSize = fileObject.toHumanSize(fileObject.size);
        return fileObject;
    }

    public static void openFile(Context context, File file, String fileType) {
        try {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(uri, fileType);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
