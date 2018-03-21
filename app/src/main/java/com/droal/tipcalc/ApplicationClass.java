package com.droal.tipcalc;

import android.app.Application;

/**
 * Created by aleydario on 24/03/17.
 */

public class ApplicationClass extends Application {

    private String urlMenu = "https://developer.android.com/guide/topics/resources/menu-resource.html";

    private String urlStrings = "https://developer.android.com/guide/topics/resources/string-resource.html?hl=es-419";

    public String getUrlMenu() {
        return urlMenu;
    }

    public String getUrlStrings() {
        return urlStrings;
    }
}
