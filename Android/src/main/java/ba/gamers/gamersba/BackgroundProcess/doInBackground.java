package ba.gamers.gamersba.BackgroundProcess;

/**
 * Created by Amar on 13.11.2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ba.gamers.gamersba.MyActivity;
import ba.gamers.gamersba.R;
import ba.gamers.gamersba.RealActivity.WebActivity;

public class doInBackground extends Activity {
    private EditText username = null;
    private EditText password = null;
    private TextView attempts;
    private EditText time;
    private TextView finalResult;
    private Button login;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        private WebView webView;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Login na sajt u toku..."); // Calls onProgressUpdate()
            try {
                // Do your long operations here and return the result
                //int time = Integer.parseInt(params[0]);
                // Sleeping for given time period
                //Thread.sleep(time);
                //resp = "Slept for " + time + " milliseconds";
                //boolean loadhomepage = false;
                Intent i = getIntent();
                Intent i2 = getIntent();
                if (i.hasExtra("aKey")){
                    String username = i.getStringExtra("aKey");}
                if (i2.hasExtra("bKey")){
                    String password = i2.getStringExtra("bKey");}
                final String possibleUrl = "http://www.gamers.ba/";
                final String possibleUrl1 = "http://gamers.ba/";
                webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("http://www.gamers.ba/q/user/login");
                final String currentUrl = webView.getUrl();
                webView.setWebViewClient(new WebViewClient() {

                    public void onPageFinished(WebView view, String url) {
                        webView.loadUrl("javascript:(function(){document.getElementById('" + username + "').value='MyUsername';document.getElementById('" + password + "').value='MyPassword';document.forms.loginForma.submit.click();})();");
                        if (currentUrl.equals(possibleUrl)) {
                            Intent myIntent = new Intent(doInBackground.this, WebActivity.class); //view.getContext()
                            startActivity(myIntent);
                            Toast.makeText(getApplicationContext(),
                                    "Login uspjesan!", Toast.LENGTH_LONG).show();
                        } else if (currentUrl.equals(possibleUrl1)) {
                            Intent myIntent = new Intent(doInBackground.this, WebActivity.class); //view.getContext()
                            startActivity(myIntent);
                            Toast.makeText(getApplicationContext(),
                                    "Login uspjesan!", Toast.LENGTH_LONG).show();
                        } else {
                            Intent myIntent1 = new Intent(doInBackground.this, MyActivity.class); //view.getContext()
                            startActivity(myIntent1);
                            Toast.makeText(getApplicationContext(),
                                    "Pogresan username ili password!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            /*} catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();*/
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            finalResult.setText(result);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}