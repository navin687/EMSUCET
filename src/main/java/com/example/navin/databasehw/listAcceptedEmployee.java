package com.example.navin.databasehw;

import android.content.DialogInterface;
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

public class listAcceptedEmployee extends AppCompatActivity
{
    SQLiteDatabase db;
    ListView lv;
    ArrayList al,al1;
    AlertDialog ad1,ad2;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_accepted_employee);
        lv=(ListView)findViewById(R.id.lv1);
        mAuth = FirebaseAuth.getInstance();
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
                    String qry="select * from student where idno="+idno+"";
                    Cursor c = db.rawQuery(qry, null);
                    c.moveToFirst();
                    final int IDNO=c.getInt(0);
                    String NAME=c.getString(1);
                    int AGE=c.getInt(2);
                    String CNO=c.getString(3);
                    final String EMAIL= c.getString(4);
                    String STATUS=c.getString(6);
                    final String pwd=c.getString(5);

                     String message="Idno="+idno+",\nname='"+NAME+"' , \nage="+AGE+",\nContact no="+CNO+",\nemail="+EMAIL+",\nstatus="+STATUS+"";
                   ad1= new AlertDialog.Builder(listAcceptedEmployee.this)
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
                                    ad2=new AlertDialog.Builder(listAcceptedEmployee.this)
                                            .setMessage("Are you sure want to delete")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String qry="delete from student where idno="+IDNO+"";
                                                    db.execSQL(qry);
                                                    al.remove(position);
                                                    aa.notifyDataSetChanged();
                                                    Toast.makeText(listAcceptedEmployee.this, "Deleting the data from local database please visit online datatbase", Toast.LENGTH_LONG).show();
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
                           .setNeutralButton("decline", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which)
                               {
                                   String qry="update student set status='decline' where idno="+IDNO+"";
                                   db.execSQL(qry);
                                   mAuth.signInWithEmailAndPassword(EMAIL,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                       @Override
                                       public void onComplete(@NonNull Task<AuthResult> task)
                                       {
                                           FirebaseUser curr_user= FirebaseAuth.getInstance().getCurrentUser();
                                           String uid=curr_user.getUid();
                                           String stat="decline";
                                           mdatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                                           mdatabase.child("status").setValue(stat);

                                           Toast.makeText(listAcceptedEmployee.this, "user decline", Toast.LENGTH_SHORT).show();

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

    String qry = "select idno,name,status from student where status='accepted'";
    Cursor c = db.rawQuery(qry, null);
    if (c.moveToFirst())
    {
        do {
            int idno = c.getInt(0);
            String name=c.getString(1);


            al1.add(idno);
            al.add(idno+" "+name);
        } while (c.moveToNext());
}
        else
        {
            Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}
