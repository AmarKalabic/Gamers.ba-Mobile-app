/*

                                           ////TO DO - PRIMARY ////

                                           - Fixati home i refresh button
                                           - Logout link checker
                                           - Ako je user vec logovan ne prikazivati login screen jer se sprema kad si loginovan automatski u browseru...
                                           - Optimizacija koda
                                           - Dodati jsoup checker sa notification timerom
                                           - Fixati publishProgress (nestane sekundu nakon pozivanja)
                                           - Dodati jos publishProgressa za: Otvaranje stranice... ; Pokretanje login skripte... ;Login na stranicu... ;

                                           ////TO DO - SECONDARY////
                                           - Remember me checkbox
                                           - Provjeravaj poruke, guestbook i notifikacije svakih: x sekundi <-- opcija
                                           - UI uljepsavanja


 */

package ba.gamers.gamersba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ba.gamers.gamersba.LoginScreen.Login;
import ba.gamers.gamersba.RealActivity.WebActivity;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static ba.gamers.gamersba.R.*;
import static ba.gamers.gamersba.R.layout.*;

public class MyActivity extends Activity {

    private Boolean clicked = false;

    private EditText username = null;
    private EditText password = null;
    private TextView attempts;
    private EditText time;
    private TextView finalResult;
    private Button login;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    int counter = 3;
    private String username1, password1;

    /*private class MyJavaScriptInterface {
        @JavascriptInterface
        public void handleHtml(String html) {
            // Use jsoup on this String here to search for your content.
            Document doc = Jsoup.parse(html);

            // Now you can, for example, retrieve a div with id="username" here
            Element usernameDiv = doc.select("#logovan").first();
            if (usernameDiv != null) {

            }
        }
    }*/

    private ProgressBar spinner;
    private ImageButton error_imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ba.gamers.gamersba.R.layout.activity_logo);
        spinner = (ProgressBar)findViewById(id.progressBar1);
        error_imagebutton = (ImageButton)findViewById(id.imageButton);
        //spinner.setVisibility(View.VISIBLE);
        /*Connection.Response loginForm = Jsoup.connect("http://gamers.ba/q/login")
                .method(Connection.Method.GET)
                .execute();

        Document document = Jsoup.connect("http://gamers.ba/q/login")
                .data("cookieexists", "false")
                .data("username", "username")
                .data("password", "pass")
                .data("submit", "Login")
                .cookies(loginForm.cookies())
                .post();

            if (clicked == false) {
            System.out.println("Clicked is false!");
            AsyncTaskRunner runner = new AsyncTaskRunner();
            //String username = username.getText().toString();
            //String password = password.getText().toString();
            String fake_username = "username";
            String fake_password = "password";
            runner.execute(fake_username, fake_password, String.valueOf(clicked));
        } else if (clicked == true) {*/
            //time = (EditText) findViewById(R.id.et_time);

        AsyncTaskRunner runner = new AsyncTaskRunner();
                            //String username = username.getText().toString();
                            //String password = password.getText().toString();
        runner.execute();
                        //});
    }

    public void onClickError(View view) {
        error_imagebutton = (ImageButton)findViewById(id.imageButton);
        spinner = (ProgressBar)findViewById(id.progressBar1);
        spinner.setVisibility(View.GONE);
        error_imagebutton.setVisibility(View.VISIBLE);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
    }


    /*private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.button1:
                    //DO something
                    /*Intent intent = new Intent(MyActivity.this, doInBackground.class);
                    intent.putExtra("aKey", username.getText().toString());
                    startActivity(intent);
                    Intent intent1 = new Intent(MyActivity.this, doInBackground.class);
                    intent1.putExtra("bKey", password.getText().toString());
                    startActivity(intent1);
                    Intent myIntent = new Intent(MyActivity.this, doInBackground.class); //view.getContext()
                    startActivity(myIntent);
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    //String username = username.getText().toString();
                    //String password = password.getText().toString();
                    runner.execute(getUsername(), getPassword());
                    break;
            }
        }
    };*/

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        public WebView webView;
        private Boolean loggedin;

        private boolean running = true;

        @Override
        protected void onCancelled() {
            running = false;
        }

        public class MyJavaScriptInterface {
            @JavascriptInterface
            public void handleHtml(String html) {
                // Use jsoup on this String here to search for your content.
                Document doc = Jsoup.parse(html);

                // Now you can, for example, retrieve a div with id="username" here
                Element usernameDiv = doc.select("#logovan").first();
                if (usernameDiv != null) {
                    loggedin = true;
                } else {
                    loggedin = false;
                }
            }
        }

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            loggedin = false;

            /*class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Use jsoup on this String here to search for your content.
                    Document doc = Jsoup.parse(html);

                    // Now you can, for example, retrieve a div with id="username" here
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                    }else{
                        loggedin = false;
                    }
                }
            }*/


        }

        /*loggedin = false;

        private class MyJavaScriptInterface {
            @JavascriptInterface
            public void handleHtml(String html) {
                // Use jsoup on this String here to search for your content.
                Document doc = Jsoup.parse(html);

                // Now you can, for example, retrieve a div with id="username" here
                Element usernameDiv = doc.select("#logovan").first();
                if (usernameDiv != null) {
                   loggedin = true;
                }else{
                    loggedin = false;
                }
            }
        }*/

        @Override
        protected String doInBackground(String... params) {


            class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Jsoup string
                    Document doc = Jsoup.parse(html);

                    // Div search
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                        System.out.println("User je logovan!");
                        running = false;
                        Intent myIntent = new Intent(MyActivity.this, WebActivity.class); //view.getContext()
                        startActivity(myIntent);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // runs on UI thread
                                Toast.makeText(getApplicationContext(),
                                        "Login uspjesan!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    } else {
                        System.out.println("User NIJE logovan!");
                        loggedin = false;
                        Intent myIntent = new Intent(MyActivity.this, Login.class); //view.getContext()
                        startActivity(myIntent);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                    }
                }
            }
            while (running == true) {
                try {
                    //Toast.makeText(getApplicationContext(), "Try started!", Toast.LENGTH_LONG).show();
                    // Do long operations here and return the result
                    //int time = Integer.parseInt(params[0]);
                    // Sleeping for given time period
                    //Thread.sleep(time);
                    //resp = "Slept for " + time + " milliseconds";
                    //boolean loadhomepage = false;
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //stuff that update ui
                        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview1);

                    }
                });*/
                    runOnUiThread(new Runnable() {
                        public void run() {
                            webView = (WebView) findViewById(id.webview2);
                            webView.getSettings().setJavaScriptEnabled(true);
                            webView.loadUrl("http://www.gamers.ba/q/user/login");
                            webView.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlHandler");
                            //android:visibility="gone"
                            //webView.setVisibility(View.GONE);
                            final String currentUrl = webView.getUrl();
                            final boolean[] loadedOnce = new boolean[1];
                            loadedOnce[0] = false;
                            webView.setWebViewClient(new WebViewClient() {

                                public void onPageFinished(WebView view, String url) {
                                    //Toast.makeText(getApplicationContext(), "onPageFinished OK!", Toast.LENGTH_LONG).show();
                                    //webView.loadUrl("javascript:(function(){document.getElementById('" + usernamee + "').value='MyUsername';document.getElementById('" + passwordd + "').value='MyPassword';document.forms.loginForma.submit.click();})();");
                                    //Toast.makeText(getApplicationContext(), "Javacsript loaded!", Toast.LENGTH_LONG).show();
                                    //System.out.println( "Finished loading: " + url );
                                    webView.loadUrl("javascript:window.HtmlHandler.handleHtml" +
                                            "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                                    //Debugger: System.out.println("Finished loading: " + url);
                                }

                                @Override
                                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                    error_imagebutton = (ImageButton)findViewById(id.imageButton);
                                    spinner = (ProgressBar)findViewById(id.progressBar1);
                                    spinner.setVisibility(View.GONE);
                                    error_imagebutton.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //    resp = e.getMessage();
            }catch(Exception e){
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        return null;
    }
}


        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        //@Override
        protected void onPostExecute(String result) {
        }


        ///*
        // * (non-Javadoc)
        // *
        // * @see android.os.AsyncTask#onPreExecute()

        /*@Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            loggedin = false;

            public class MyJavaScriptInterface {
                @JavascriptInterface
                public void handleHtml(String html) {
                    // Use jsoup on this String here to search for your content.
                    Document doc = Jsoup.parse(html);
                    // Now you can, for example, retrieve a div with id="username" here
                    Element usernameDiv = doc.select("#logovan").first();
                    if (usernameDiv != null) {
                        loggedin = true;
                    }else{
                        loggedin = false;
                    }
                }
            }*/



        ///*
        // * (non-Javadoc)
        // *
        // * @see android.os.AsyncTask#onProgressUpdate(Progress[])
        // */
        //@Override
        protected void onProgressUpdate(String... text) {
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }

    /*private WebView webView;

    @Override
    public void onStart() {
        super.onStart();
        webView = (WebView) findViewById(ba.gamers.gamersba.R.id.webview1);
        webView.loadUrl("http://www.gamers.ba/");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopMakingThatPeriodicRestCall();
    }*/

}

    //public void login(View view){
    //    Intent myIntent = new Intent(this, WebActivity.class); //view.getContext()
    //    startActivity(myIntent); //startActivityForResult(myIntent, 0);
        /*if(username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(), "Redirecting...",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            attempts.setBackgroundColor(Color.RED);
            counter--;
            attempts.setText(Integer.toString(counter));
            if(counter==0){
                login.setEnabled(false);
            }*/