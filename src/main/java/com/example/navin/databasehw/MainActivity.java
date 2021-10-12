package com.example.navin.databasehw;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity
{
 SQLiteDatabase db;
    Context context=this;
    ProgressDialog mprogress;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    int i;
    String stat;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //progressdialog

        mprogress=new ProgressDialog(this);


    }
    public void openRegistration(View v)
    {
        Intent i=new Intent(this,Registration1.class);
        startActivity(i);

    }

    public void openLogin(View v)
    {
        LayoutInflater li=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view=li.inflate(R.layout.activity_login_page,null);
       final AlertDialog al= new AlertDialog.Builder(this)
               .setCancelable(true)
                .setView(view)
                .create();
        al.show();
        final EditText et1=(EditText)view.findViewById(R.id.uname);
        final EditText et2=(EditText)view.findViewById(R.id.pwd);
        Button b=(Button)view.findViewById(R.id.login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Intent i=new Intent(MainActivity.this,AdminActivity.class);
                            startActivity(i);
                            al.dismiss();
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
                                                        Intent i1 = new Intent(MainActivity.this, acceptedEmployee.class);
                                                        i1.putExtra("K1", current_uid);
                                                        startActivity(i1);
                                                        finish();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(MainActivity.this, "Account has been suspended please contact admin", Toast.LENGTH_SHORT).show();
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
                                            et1.setError("Username does not exist");
                                            et1.requestFocus();
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
                                        /*Intent i1 = new Intent(context, acceptedEmployee.class);
                                        startActivity(i1);
                                        al.dismiss();
                                        */

                                    }
                                    if (stat.equals("decline")) {
                                        new AlertDialog.Builder(context)
                                                .setCancelable(false)
                                                .setTitle("Employee Detail")
                                                .setMessage("Account has been declined by admin.\nPlease contact admin")
                                                .setPositiveButton("ok", null)
                                                .create()
                                                .show();
                                        al.dismiss();


                                    }
                                    if (stat.equals("pending")) {
                                        new AlertDialog.Builder(context)
                                                .setMessage("waiting for confirmation")
                                                .setTitle("Employee Status")
                                                .setCancelable(false)
                                                .setPositiveButton("ok", null)
                                                .create()
                                                .show();
                                        al.dismiss();

                                    }
                                }
                               /* else
                                      {
                                         mAuth.signInWithEmailAndPassword(unam,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                         {
                                             @Override
                                             public void onComplete(@NonNull Task<AuthResult> task)
                                             {
                                                 if (task.isSuccessful())
                                                 {
                                                     Intent i1=new Intent(context,acceptedEmployee.class);
                                                     startActivity(i1);
                                                 }
                                                 else
                                                 {
                                                     Toast.makeText(MainActivity.this, "Username or password doesnot exist", Toast.LENGTH_SHORT).show();
                                                     et1.setText("");
                                                     et2.setText("");
                                                 }

                                             }
                                         });

                                      } */





                            }
                        }






                    }
                }

            }
        });


    }
}
