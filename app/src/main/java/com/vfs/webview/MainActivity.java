package com.vfs.webview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private WebView webView;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);

        webView.loadUrl("https://rideshare.php-dev.in/rideshare/public/payment");
        webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
    }

    class WebViewClientImpl extends WebViewClient {
        public WebViewClientImpl(MainActivity mainActivity) {
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            super.shouldOverrideUrlLoading(view, url);
            Log.d(TAG, "shouldOverrideUrlLoading: called :" + url + "___" + flag);




            /*AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: called ak2:");
                    //TODO your background code
                    URL aURL = null;
                    Log.d(TAG, "run: called ak3:");
                    try {
                        aURL = new URL(url);
                        Log.d(TAG, "run: called ak4:");
                        URLConnection conn = aURL.openConnection();
                        Log.d(TAG, "run: called ak5:");
                        conn.connect();
                        Log.d(TAG, "run: called ak6:");
                        InputStream is = conn.getInputStream();
                        Log.d(TAG, "run: called ak1:"+is);
                    } catch (Exception e){
                        Log.d(TAG, "run: called ak exce:"+e);
                    }
                }
            });*/


            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /*if (url.contains("payment_method_nonce")) {
                flag = true;
            }*/
            webView.loadUrl("javascript:HtmlViewer.showHTML" +
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        }
    }

    class MyJavaScriptInterface {

        private Context ctx;
        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            Log.d(TAG, "showHTML: called ak sja:"+html);
            System.out.println(html);
        }

    }
}


