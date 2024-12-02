package com.example.sh;

import android.app.Application;

public class UserSettings extends Application {
    public final static String PREFERENCES = "preferences";
    public final static String CUSTOM_NAME = "customTheme";
    public final static String LIGHT_THEME = "lightTheme";
    public final static String DARK_THEME = "darkTheme";

    private String CustomTheme;

    public String getCustomTheme() {
        return CustomTheme;
    }

    public void setCustomTheme(String customTheme) {
        CustomTheme = customTheme;
    }
}
