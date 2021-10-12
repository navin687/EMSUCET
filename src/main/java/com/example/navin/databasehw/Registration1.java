package com.example.navin.databasehw;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registration1 extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6, et7;
    SQLiteDatabase db;
    TextInputLayout til;
    String email, pwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);
        til = (TextInputLayout) findViewById(R.id.til6);
        executeId();

    }

    public void saveDetails(View v) {
        String name = et2.getText().toString().trim();
        String age = et3.getText().toString().trim();
        String cno = et4.getText().toString().trim();
        //email for email id
        email = et5.getText().toString().trim();
        //pwd for password storing
        pwd = et6.getText().toString().trim();
        String repwd = et7.getText().toString().trim();
        if (name.isEmpty()) {
            et2.setError("Empty");
            et2.requestFocus();
        } else {
            if (age.isEmpty()) {
                et3.setError("Empty");
                et3.requestFocus();
            } else {
                if (cno.isEmpty()) {
                    et4.setError("Empty");
                    et4.requestFocus();
                } else {
                    if (email.isEmpty()) {
                        et5.setError("Empty");
                        et5.requestFocus();
                    } else {


                        if (pwd.isEmpty())
                        {
                            et6.setError("Empty");
                            et6.requestFocus();

                        } else
                            {
                              //for password security
                                if(pwd.length()<10 &&!isValidPassword(pwd)){
                                    et6.setError("Password must be greater than 10 or use upper case as well as lower case");
                                     et6.requestFocus();
                                }




                            if (repwd.isEmpty()) {
                                et7.setError("Empty");
                                et7.requestFocus();



                            } else {
                                if (pwd.equals(repwd) == false) {
                                    et7.setError("Password is not Matching");
                                    et7.requestFocus();
                                } else
                                    {
                                    int AGE = Integer.parseInt(age);

                                    String idno = et1.getText().toString().trim();
                                    int IDNO = Integer.parseInt(idno);
                                    if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
                                    {

                                        et5.setError("Invalid Email Address");
                                        et5.requestFocus();

                                    }

                                    String qry = "insert into student values(" + IDNO + ",'" + name + "'," + AGE + "," + cno + ",'" + email + "','" + pwd + "','pending')";



                                    db.execSQL(qry);
                                    Toast.makeText(Registration1.this, "data inserted", Toast.LENGTH_SHORT).show();

                                    et2.setText("");
                                    et3.setText("");
                                    et4.setText("");
                                    et5.setText("");
                                    et6.setText("");
                                    et7.setText("");
                                    executeId();
                                }


                            }

                        }
                    }
                }
            }
        }


    }

    public void executeId() {
        MyDatabase md = new MyDatabase(this);

        db = md.getWritableDatabase();
        String qry = ("select Max(idno) from student");
        Cursor c = db.rawQuery(qry, null);
        if (c.moveToFirst()) {
            int id = c.getInt(0);

            if (id == 0) {
                id = 1211;

            } else {
                id++;
            }
            et1.setEnabled(false);
            et1.setText("" + id);
        }
    }


    //for password security
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}
