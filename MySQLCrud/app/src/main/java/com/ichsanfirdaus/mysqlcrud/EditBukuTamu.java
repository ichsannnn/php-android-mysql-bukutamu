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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditBukuTamu extends Activity {

    EditText txtName;
    EditText txtEmail;
    EditText txtDesc;
    EditText txtCreateAt;
    Button btnSave;
    Button btnDelete;
    String pid;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private static final String url_pendaftaran_details = "http://192.168.100.4/pendaftaran/get_pendaftaran_details.php";
    private static final String url_update_pendaftaran = "http://192.168.100.4/pendaftaran/update_pendaftaran.php";
    private static final String url_delete_pendaftaran = "http://192.168.100.4/pendaftaran/delete_pendaftaran.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PENDAFTARAN = "pendaftaran";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buku_tamu);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        Intent i = getIntent();
        pid = i.getStringExtra(TAG_PID);
        new GetPendaftaranDetails().execute();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new SavePendaftaranDetails().execute();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new DeletePendaftaran().execute();
            }
        });
    }

    class GetPendaftaranDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditBukuTamu.this);
            pDialog.setMessage("Loading data, please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int success;
                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));
                        JSONObject json = jsonParser.makeHttpRequest(url_pendaftaran_details, "GET", params);
                        Log.d("Single pendaftaran Details", json.toString());
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            JSONArray pendaftaranObj = json.getJSONArray(TAG_PENDAFTARAN);
                            JSONObject pendaftaran = pendaftaranObj.getJSONObject(0);
                            txtName = (EditText) findViewById(R.id.inputName);
                            txtEmail = (EditText) findViewById(R.id.inputEmail);
                            txtDesc = (EditText) findViewById(R.id.inputDesc);

                            txtName.setText(pendaftaran.getString(TAG_NAME));
                            txtEmail.setText(pendaftaran.getString(TAG_EMAIL));
                            txtDesc.setText(pendaftaran.getString(TAG_DESCRIPTION));
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }

    class SavePendaftaranDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditBukuTamu.this);
            pDialog.setMessage("Saving data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String name = txtName.getText().toString();
            String email = txtEmail.getText().toString();
            String description = txtDesc.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_EMAIL, email));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

            JSONObject json = jsonParser.makeHttpRequest(url_update_pendaftaran, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent i = getIntent();
                    setResult(100, i);
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

    class DeletePendaftaran extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditBukuTamu.this);
            pDialog.setMessage("Deleting data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid", pid));

                JSONObject json = jsonParser.makeHttpRequest(url_delete_pendaftaran, "POST", params);
                Log.d("Hapus data", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Intent i = getIntent();
                    setResult(100, i);
                    finish();
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
