package com.example.navin.databasehw;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.navin.databasehw.Registration1.isValidPassword;

public class SignupActivity extends AppCompatActivity {
    LinearLayout LogN;
    EditText et1, et2, et3, et4, et5, et6, et7;
    SQLiteDatabase db;
    Context context;
    // TextInputLayout til;
    String email, pwd;
    int abc, abcd;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        LogN = findViewById(R.id.lgnow);
        signup = findViewById(R.id.signupnow);

        //     et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);
        //  til = (TextInputLayout) findViewById(R.id.til6);
        gen();
        executeId();
        setHideKeyboardOnTouch(SignupActivity.this, findViewById(R.id.activity_sgup));

        LogN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


                                if (pwd.isEmpty()) {
                                    et6.setError("Empty");
                                    et6.requestFocus();

                                } else {
                                    //for password security
                                    if (pwd.length() < 10 && !isValidPassword(pwd)) {
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
                                        } else {
                                            int AGE = Integer.parseInt(age);
                                            //    String idno = String.valueOf(abc);
                                            String idno = String.valueOf(abcd);

                                            //     String idno = et1.getText().toString().trim();
                                            int IDNO = Integer.parseInt(idno);
                                            if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                                                et5.setError("Invalid Email Address");
                                                et5.requestFocus();

                                            }

                                            String qry = "insert into student values(" + IDNO + ",'" + name + "'," + AGE + "," + cno + ",'" + email + "','" + pwd + "','pending')";


                                            db.execSQL(qry);
                                            Toast.makeText(SignupActivity.this, "data inserted", Toast.LENGTH_SHORT).show();

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
        });
    }

    private void setHideKeyboardOnTouch(SignupActivity signupActivity, View view) {
        try {
            if (!(view instanceof EditText || view instanceof ScrollView)) {

                view.setOnTouchListener(new View.OnTouchListener() {


                    public boolean onTouch(View v, MotionEvent event) {

                        InputMethodManager in = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);

                        in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        return false;
                    }


                });
            }
//If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {

                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                    View innerView = ((ViewGroup) view).getChildAt(i);

                    setHideKeyboardOnTouch(this, innerView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        abc = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        Log.d("uid abc", String.valueOf(abc));
        return abc;
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
            abcd = id;

            //  Toast.makeText(SignupActivity.this, "unique id :" + id, Toast.LENGTH_LONG).show();
            //  et1.setEnabled(false);
            //   et1.setText("" + id);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SignupActivity.this,LgsnActivity.class);
        startActivity(i);
        finish();
    }
}
