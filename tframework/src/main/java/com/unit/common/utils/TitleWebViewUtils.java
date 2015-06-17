package com.unit.common.utils;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by longbin on 15/2/7.
 */
public class TitleWebViewUtils {

    private Context context;
    private String title;

    public TitleWebViewUtils(Context context) {
        this.context = context;
    }

    public WebView getWebView() {
        WebView webView = new WebView(context);
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                TitleWebViewUtils.this.title = title;
            }

        };
        webView.setWebChromeClient(webChromeClient);
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        return webView;
    }


    public String getTitle() {
        return title;
    }
}
