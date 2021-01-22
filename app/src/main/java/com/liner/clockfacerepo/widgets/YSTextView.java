package com.liner.clockfacerepo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;


import androidx.appcompat.widget.AppCompatTextView;

import com.liner.clockfacerepo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class YSTextView extends AppCompatTextView {
    private final String COLOR_ENTRY_PATTERN = "\\{c=([#0-9a-eA-E]{7}), t=([a-zA-Z0-9!?,. ~@#$%^&*()_\\-+|]*)\\}";

    public enum FontType{
        BOLD ,
        LIGHT ,
        MEDIUM ,
        REGULAR,
        REGULAR_ITALIC,
        THIN
    }

    private String spannableText;
    private Typeface typeface;

    public YSTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public YSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public YSTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.YSTextView, defStyle, 0);
        int fontType = typedArray.getInt(R.styleable.YSTextView_font_type, 4);
        spannableText = typedArray.getString(R.styleable.YSTextView_spannable_text);
        typedArray.recycle();
        switch (fontType) {
            case 1:
                typeface = Typeface.createFromAsset(context.getAssets(), "yandex_font/Bold.ttf");
                break;
            case 2:
                typeface = Typeface.createFromAsset(context.getAssets(), "yandex_font/Light.ttf");
                break;
            case 3:
                typeface = Typeface.createFromAsset(context.getAssets(), "yandex_font/Medium.ttf");
                break;
            case 4:
                typeface = Typeface.createFromAsset(context.getAssets(), "yandex_font/Regular.ttf");
                break;
            case 5:
                typeface = Typeface.createFromAsset(context.getAssets(), "yandex_font/RegularItalic.ttf");
                break;
            case 6:
                typeface = Typeface.createFromAsset(context.getAssets(), "yandex_font/Thin.ttf");
                break;
        }
        if(spannableText != null)
            paintSpannable(extractColors(spannableText), spannableText);
        setTypeface(typeface);
    }


    public void setSpannableText(String spannableText) {
        this.spannableText = spannableText;
        paintSpannable(extractColors(spannableText), spannableText);
    }

    private void setFontType(FontType fontType) {
        switch (fontType){
            case REGULAR:
                typeface = Typeface.createFromAsset(getContext().getAssets(), "yandex_font/Regular.ttf");
                break;
            case BOLD:
                typeface = Typeface.createFromAsset(getContext().getAssets(), "yandex_font/Bold.ttf");
                break;
            case THIN:
                typeface = Typeface.createFromAsset(getContext().getAssets(), "yandex_font/Thin.ttf");
                break;
            case LIGHT:
                typeface = Typeface.createFromAsset(getContext().getAssets(), "yandex_font/Light.ttf");
                break;
            case MEDIUM:
                typeface = Typeface.createFromAsset(getContext().getAssets(), "yandex_font/Medium.ttf");
                break;
            case REGULAR_ITALIC:
                typeface = Typeface.createFromAsset(getContext().getAssets(), "yandex_font/RegularItalic.ttf");
                break;
        }
        setTypeface(typeface);
    }


    private List<ColorEntry> extractColors(String spannableText){
        List<ColorEntry> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(COLOR_ENTRY_PATTERN);
        Matcher matcher = pattern.matcher(spannableText);
        while (matcher.find()){
            ColorEntry colorEntry = new ColorEntry();
            colorEntry.key = matcher.group(0);
            colorEntry.text = matcher.group(2);
            colorEntry.color = Color.parseColor(matcher.group(1));
            result.add(colorEntry);
        }
        return result;
    }

    private void paintSpannable(List<ColorEntry> colorEntries, String spannableText){
        String clearedText = clearSpannable(spannableText, colorEntries);
        Spannable spannable = new SpannableString(clearedText);
        for(ColorEntry colorEntry : colorEntries){
            int startIndex = clearedText.indexOf(colorEntry.text);
            int endIndex = startIndex + colorEntry.text.length();
            spannable.setSpan(new ForegroundColorSpan(colorEntry.color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannable);
    }

    private String clearSpannable(String spannableText, List<ColorEntry> colorEntryList){
        String result = spannableText;
        for(ColorEntry colorEntry : colorEntryList)
            result = result.replace(colorEntry.key, colorEntry.text);
        return result;
    }

    private static class ColorEntry {
        public String key;
        public String text;
        public int color;

        @Override
        public String toString() {
            return "ColorText{" +
                    "key='" + key + '\'' +
                    ", text='" + text + '\'' +
                    ", color=" + color +
                    '}';
        }
    }
}
