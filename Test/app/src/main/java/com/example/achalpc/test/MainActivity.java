package com.example.achalpc.test;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG ="Main Activity" ;
    //Defining views
    private EditText editTextName;
    private EditText editTextDesg;
    private EditText editTextSal;

    private Button buttonAdd;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDesg = (EditText) findViewById(R.id.editTextDesg);
        editTextSal = (EditText) findViewById(R.id.editTextSalary);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);

        TextView textViewIp = (TextView) findViewById(R.id.active_ip);
        textViewIp.setText(getResources().getString(R.string.ip_string)+ " " + Config.getIPAddress(true));
        textViewIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You're on a Secured Network", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Adding an employee
    private void addEmployee(){


        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim();
        final String sal = editTextSal.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected String doInBackground(Void... v) {
                //String check="";
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,name);
                params.put(Config.KEY_EMP_DESG,desg);
                params.put(Config.KEY_EMP_SAL,sal);
//                for(Map.Entry m: params.entrySet()){
//                    check+=m.getKey()+" "+m.getValue()+"\n";
//                }
//                Log.v(TAG,"KEY/VALUE : "+ check);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Adding Employee","Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addEmployee();
        }

        if(v == buttonView){
            startActivity(new Intent(this,ViewAllEmployee.class));
        }
    }
}