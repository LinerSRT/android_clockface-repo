package com.liner.clockfacerepo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileFormats {
    public interface Image {
        String PNG = "image/png";
        String JPG = "image/jpg";
        String JPEG = "image/jpeg";
        String WEBP = "image/webp";
        String GIF = "image/gif";
        String SVG = "image/svg+xml";

        List<String> SUPPORTED_LIST = Collections.unmodifiableList(new ArrayList<String>() {
            {
                add(PNG);
                add(JPG);
                add(JPEG);
                add(WEBP);
                add(GIF);
                add(SVG);
            }
        });
    }

    public interface Audio {
        String RFC = "audio/basic";
        String PCM = "audio/L24";
        String MP4 = "audio/mp4";
        String AAC = "audio/aac";
        String MPEG = "audio/mpeg";
        String OGG = "audio/ogg";
        String VORBIS = "audio/vorbis";

        List<String> SUPPORTED_LIST = Collections.unmodifiableList(new ArrayList<String>() {
            {
                add(RFC);
                add(PCM);
                add(MP4);
                add(AAC);
                add(MPEG);
                add(OGG);
                add(VORBIS);
            }
        });
    }


    public interface File {
        String OCTET = "application/octet-stream";
        String OGG = "application/ogg";
        String ZIP = "application/zip";
        String GZIP = "application/gzip";

        List<String> SUPPORTED_LIST = Collections.unmodifiableList(new ArrayList<String>() {
            {
                add(OCTET);
                add(OGG);
                add(ZIP);
                add(GZIP);
            }
        });
    }

    public interface Text {
        String HTML = "text/html";
        String PLAIN = "text/plain";
        String XML = "application/xml";
        String PDF = "application/pdf";
        String JSON = "application/json";
        String JAVASCRIPT = "application/javascript";

        List<String> SUPPORTED_LIST = Collections.unmodifiableList(new ArrayList<String>() {
            {
                add(PDF);
                add(JSON);
                add(JAVASCRIPT);
                add(XML);
                add(HTML);
                add(PLAIN);
            }
        });
    }

    public interface Video {
        String MPEG = "video/mpeg";
        String MP4 = "video/mp4";
        String OGG = "video/ogg";
        String WEBM = "video/webm";
        String TREE_GPP = "video/3gpp";
        String TREE_GPP2 = "video/3gpp2";

        List<String> SUPPORTED_LIST = Collections.unmodifiableList(new ArrayList<String>() {
            {
                add(MPEG);
                add(MP4);
                add(OGG);
                add(WEBM);
                add(TREE_GPP);
                add(TREE_GPP2);
            }
        });
    }
}
