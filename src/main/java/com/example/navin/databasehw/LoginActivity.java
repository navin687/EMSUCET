package com.example.navin.databasehw;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
Button logIn,SigUp;
LinearLayout CreateN;


    SQLiteDatabase db;
    Context context=this;
    ProgressDialog mprogress;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    int i;
    String stat;
    private FirebaseUser currentUser;
    EditText et1,et2;
    CheckBox checkBox;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logIn =  findViewById(R.id.logIn);
        SigUp =  findViewById(R.id.sgup);
        CreateN =  findViewById(R.id.crtnow);
        checkBox = findViewById(R.id.checkBox);
        session = new SessionManager(getApplicationContext());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    et2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    et2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        //progressdialog

        mprogress=new ProgressDialog(this);

          et1=(EditText)findViewById(R.id.uname);
          et2=(EditText)findViewById(R.id.pwd);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mprogress.setTitle("Authenticating");
                mprogress.setCanceledOnTouchOutside(false);
                mprogress.setMessage("Loading Please wait....");
                mprogress.show();
                String unam=et1.getText().toString().trim();
                String pwd=et2.getText().toString().trim();
                if (unam.isEmpty())
                {
                    mprogress.hide();
                    et1.setError("Empty");
                    et1.requestFocus();
                }
                else
                {
                    if (pwd.isEmpty())
                    {
                        mprogress.hide();
                        et2.setError("Empty");
                        et2.requestFocus();
                    }
                    else
                    {
                        if ((unam.equals("sathya"))&& (pwd.equals("tech")))
                        {
                            mprogress.dismiss();
                            //session.createLoginSession("test name", "test@gmail.com");
                            session.createLoginSession();

                            Intent i=new Intent(LoginActivity.this,NavDrwActivity.class);
                            startActivity(i);
                           // al.dismiss();
                        }
                        else
                        {


                            String qry = "select Max(idno)from student where email='" + unam +"' ";
                            db=new MyDatabase(context).getWritableDatabase();
                            Cursor c = db.rawQuery(qry, null);
                            c.moveToFirst();

                            i=c.getInt(0);
                            mAuth.signInWithEmailAndPassword(unam,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful())
                                    {

                                        i = 1;
                                        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                        final String current_uid = currentUser.getUid();
                                        mdatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
                                        mdatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                stat = dataSnapshot.child("status").getValue().toString();
                                                if (stat.equals("accepted")) {
                                                    mprogress.dismiss();
                                                    session.createLoginSession();
                                                    Intent i1 = new Intent(LoginActivity.this, acceptedEmployee.class);
                                                    i1.putExtra("K1", current_uid);
                                                    startActivity(i1);
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(LoginActivity.this, "Account has been suspended please contact admin", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {


                                            }
                                        });

                                    }
                                    else
                                    {
                                        mprogress.hide();
                                        Toast.makeText(LoginActivity.this,"Username does not exist",Toast.LENGTH_LONG).show();
                                       // et1.setError("Username does not exist");
                                        //et1.requestFocus();
                                    }

                                }
                            });

                            if (i==0)
                            {
                                //et1.setError("Username does not exist");
                                //et1.requestFocus();


                            }
                            else
                            {

                                String qry1="select Email,password,status from student where email='"+unam+"'";
                                Cursor c1=db.rawQuery(qry1,null);
                                c1.moveToFirst();
                                String email=c1.getString(0);
                                String pd=c1.getString(1);
                                String stat=c1.getString(2);

                                if (unam.equals(email)&& pwd.equals(pd))
                                {

                                    if (stat.equals("accepted")) {


                                    }
                                    if (stat.equals("decline")) {
                                        new AlertDialog.Builder(context)
                                                .setCancelable(false)
                                                .setTitle("Employee Detail")
                                                .setMessage("Account has been declined by admin.\nPlease contact admin")
                                                .setPositiveButton("ok", null)
                                                .create()
                                                .show();
                                    //    al.dismiss();


                                    }
                                    if (stat.equals("pending")) {
                                        new AlertDialog.Builder(context)
                                                .setMessage("waiting for confirmation")
                                                .setTitle("Employee Status")
                                                .setCancelable(false)
                                                .setPositiveButton("ok", null)
                                                .create()
                                                .show();
                                    //    al.dismiss();

                                    }
                                }





                            }
                        }






                    }
                }

//
  //                  Intent i = new Intent(LoginActivity.this,NavDrwActivity.class);
          //      startActivity(i);
            }
        });

        SigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
        CreateN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(LoginActivity.this,LgsnActivity.class);
        startActivity(i);
        finish();
    }
}
