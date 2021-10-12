package com.example.navin.databasehw;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class listDeclineEmployee extends AppCompatActivity
{ SQLiteDatabase db;
    ListView lv;
    ArrayList al,al1;
    AlertDialog ad1,ad2;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    String NAME,CNO,EMAIL,pwd,STATUS;
    int AGE;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_list_decline_employee);
        lv=(ListView)findViewById(R.id.lv1);
        db = new MyDatabase(this).getWritableDatabase();
        al = new ArrayList();
        al1=new ArrayList();
        arrayList();
        final ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, al);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                final int idno = (int) al1.get(position);
                final String[] qry = {"select * from student where idno=" + idno + ""};
                Cursor c = db.rawQuery(qry[0], null);
                c.moveToFirst();
                final int IDNO=c.getInt(0);
                 NAME=c.getString(1);
                 AGE=c.getInt(2);
                 CNO=c.getString(3);
                 EMAIL= c.getString(4);
                STATUS=c.getString(6);
                pwd=c.getString(5);

                String message="Idno="+idno+",\nname='"+NAME+"' , \nage="+AGE+",\nContact no="+CNO+",\nemail="+EMAIL+",\nstatus="+STATUS+"";
                ad1= new AlertDialog.Builder(listDeclineEmployee.this)
                        .setMessage(message)
                        .setTitle("Employee Detail")
                        .setCancelable(false)
                        .setPositiveButton("OK",null)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ad1.dismiss();
                                ad2=new AlertDialog.Builder(listDeclineEmployee.this)
                                        .setMessage("Are you sure want to delete")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String qry="delete from student where idno="+IDNO+"";
                                                db.execSQL(qry);
                                                al.remove(position);
                                                aa.notifyDataSetChanged();

                                                Toast.makeText(listDeclineEmployee.this, "Deleting the data from local database please visit online datatbase ", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {

                                            }
                                        })

                                        .create();
                                ad2.show();


                            }
                        })
                        .setNeutralButton("accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                               final String  qry ="update student set status='accepted' where idno="+IDNO+"";
                                db.execSQL(qry);

                                mAuth.signInWithEmailAndPassword(EMAIL,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                         if (task.isSuccessful())
                                         {
                                             FirebaseUser curr_user= FirebaseAuth.getInstance().getCurrentUser();
                                             String uid=curr_user.getUid();
                                             mdatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                             mdatabase.child("status").setValue("accepted");

                                         }
                                         else
                                         {
                                             mAuth.createUserWithEmailAndPassword(EMAIL,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                             {
                                                 @Override
                                                 public void onComplete(@NonNull Task<AuthResult> task)
                                                 {
                                                     if (task.isSuccessful())
                                                     {
                                                         Toast.makeText(listDeclineEmployee.this, "successful", Toast.LENGTH_SHORT).show();
                                                         //for changing status
                                                         String qry="update student set status='accepted' where idno="+IDNO+"";
                                                         db.execSQL(qry);
                                                         al.remove(position);
                                                         aa.notifyDataSetChanged();
                                                         //converting age int data into string
                                                         String mage=Integer.toString(AGE);

                                                         STATUS="accepted";

                                                         //Firebase Database
                                                         FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
                                                         String uid=current_user.getUid();
                                                         mdatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                                                         HashMap<String,String> usermap = new HashMap<>();
                                                         usermap.put("name",NAME);
                                                         usermap.put("age",mage);
                                                         usermap.put("contact",CNO);
                                                         usermap.put("email",EMAIL);
                                                         usermap.put("status",STATUS);
                                                         usermap.put("profile_image","image");

                                                         mdatabase.setValue(usermap);






                                                         //sending emails


                                                         Intent i = new Intent(Intent.ACTION_SEND);
                                                         i.setType("message/rfc822");
                                                         i.putExtra(Intent.EXTRA_EMAIL  , new String[]{""+EMAIL});
                                                         i.putExtra(Intent.EXTRA_SUBJECT, "this is for test purpose");
                                                         i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                                                         try {
                                                             startActivity(Intent.createChooser(i, "Send mail..."));
                                                         } catch (ActivityNotFoundException ex) {
                                                             Toast.makeText(listDeclineEmployee.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                     else
                                                     {
                                                         Toast.makeText(listDeclineEmployee.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                                                     }

                                                 }
                                             });



                                         }
                                    }
                                });


                                al.remove(position);
                                aa.notifyDataSetChanged();
                            }
                        })
                        .create();
                ad1.show();

            }
        });


    }
    public void arrayList()
    {

        String qry = "select idno,name,status from student where status='decline'";
        Cursor c = db.rawQuery(qry, null);
        if (c.moveToFirst())
        {
            do
            {
                int idno = c.getInt(0);
                String name = c.getString(1);

                al1.add(idno);
                al.add(idno + " " + name);


            } while (c.moveToNext()) ;
        }
        else
        {
            Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}
