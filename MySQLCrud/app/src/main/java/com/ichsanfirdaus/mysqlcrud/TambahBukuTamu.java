package com.ichsanfirdaus.mysqlcrud;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ichsan on 31/10/15.
 */
public class TambahBukuTamu extends Activity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputEmail;
    EditText inputDesc;
    private static String url_tambah_pendaftaran = "http://192.168.100.4/pendaftaran/create_pendaftaran.php";
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_bukutamu);

        inputName = (EditText) findViewById(R.id.inputName);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        Button btnCreatePendaftaran = (Button) findViewById(R.id.btnCreatePendaftaran);

        btnCreatePendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateNewPendaftaran().execute();
            }
        });
    }

    class CreateNewPendaftaran extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TambahBukuTamu.this);
            pDialog.setMessage("Loading, please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String description = inputDesc.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("description", description));

            JSONObject json = jsonParser.makeHttpRequest(url_tambah_pendaftaran, "POST", params);
            Log.d("Create response", json.toString());

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent i = new Intent(getApplicationContext(), SemuaBukuTamu.class);
                    startActivity(i);
                    finish();
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
}
