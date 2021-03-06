package mx.edu.itcelaya.webservicerest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;

public class loadListTask extends AsyncTask<String, Void, String> {
    String jsonResult;
    String credentials;
    private Context contexto;
    public loadListTask(Context c, String k) {
        // TODO Auto-generated constructor stub
        contexto = c;
        credentials = k;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(params[0]);
        credentials = credentials + ":";
       String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        httpget.setHeader("Authorization", "Basic " + base64EncodedCredentials);
        httpget.setHeader(HTTP.CONTENT_TYPE,"application/json");
        try {
            HttpResponse response = httpclient.execute(httpget);
            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            return jsonResult;
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            Toast.makeText(contexto, "Error..." + e2.toString(), Toast.LENGTH_LONG).show();
        }
        return "";
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        }
        catch (IOException e) {
            Toast.makeText(contexto,
                    "Error..." + e.toString(), Toast.LENGTH_LONG).show();
        }
        return answer;
    }
}