package com.kftc.openbankingsample2.common.util.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class CustomContextWrapper extends ContextWrapper {
    public CustomContextWrapper(Context base) {
        super(base);
    }

    public static CustomContextWrapper wrap(Context context, Locale newLocale) {
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context = context.createConfigurationContext(config);
        } else {
            config.setLocale(newLocale);
            context = context.createConfigurationContext(config);
        }

        return new CustomContextWrapper(context);
    }
}
