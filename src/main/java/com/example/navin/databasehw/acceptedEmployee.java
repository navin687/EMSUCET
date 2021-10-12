package com.example.navin.databasehw;

/*import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.random;

public class acceptedEmployee extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    TextView NAME, AGE, CONTACT, EMAIL;
    private static final int Gallery_pick = 1;
    private StorageReference mimageStorage;
    private ProgressDialog pd,progressupload,progressshow;
    CircleImageView mdisplayImage;
    LinearLayout hidelayout;
    Boolean a=true;
    String install,uuid;
    Button submit;
    EditText father,mother,designation,address;
    Spinner spinner1,spinner2;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_employee);
        mimageStorage = FirebaseStorage.getInstance().getReference();


        //creat menubar with logout button
        toolbar = (Toolbar)findViewById(R.id.lgout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EMS");
        //get the spinner from the xml.
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"yes", "no"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner1.setAdapter(adapter);



        //get the spinner from the xml.
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //create a list of items for the spinner.
        String[] items1 = new String[]{"no", "1","2","3","more"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        //set the spinners adapter to the previously created one.
        spinner2.setAdapter(adapter1);




        Intent il = getIntent();
        Bundle b = il.getExtras();
        uuid = b.getString("K1");
        //downcasting the edittext id
        LinearLayout ll1 = (LinearLayout) findViewById(R.id.rl1);
        father=(EditText) findViewById(R.id.textView10);
        mother=(EditText) findViewById(R.id.textView12);
        designation=(EditText) findViewById(R.id.textView14);
        address=(EditText) findViewById(R.id.textView18);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        //for button submit
        submit=(Button)findViewById(R.id.submit);


        NAME = (TextView) findViewById(R.id.textView5);
        AGE = (TextView) findViewById(R.id.textView6);
        CONTACT = (TextView) findViewById(R.id.textView7);
        EMAIL = (TextView) findViewById(R.id.textView8);
        mdisplayImage = (CircleImageView) findViewById(R.id.iv);
        Button upload_image = (Button) findViewById(R.id.upload_image);
        hidelayout=(LinearLayout) findViewById(R.id.hidelayout);


        mAuth = FirebaseAuth.getInstance();

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select_Image"), Gallery_pick);
            }
        });

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String current_uid=currentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uuid);

        mUserDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String contact = dataSnapshot.child("contact").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String image=dataSnapshot.child("profile_image").getValue().toString();
                install=dataSnapshot.child("install").getValue().toString();
                Toast.makeText(acceptedEmployee.this, "" + name, Toast.LENGTH_SHORT).show();
                NAME.setText(name);
                AGE.setText(age);
                CONTACT.setText(contact);
                EMAIL.setText(email);
                if (!image.equals("image"))
                {
                    Picasso.with(acceptedEmployee.this).load(image).placeholder(R.drawable.black3).into(mdisplayImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(acceptedEmployee.this, "error!!! Check internet connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser curentuser = mAuth.getCurrentUser();

        if (curentuser == null) {
            sendtostart();
        }
    }

    private void sendtostart() {
        Intent startIntent = new Intent(acceptedEmployee.this, LgsnActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_log_out) {
            FirebaseAuth.getInstance().signOut();
            sendtostart();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_pick && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
            // Toast.makeText(this, ""+imageUri, Toast.LENGTH_SHORT).show();

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                progressupload = new ProgressDialog(this);
                progressupload.setTitle("Uploading images..");
                progressupload.setCanceledOnTouchOutside(false);
                progressupload.setMessage("Please wait while we upload the images");
                progressupload.show();

                Uri resultUri = result.getUri();

                StorageReference filepath = mimageStorage.child("profile_images").child(random() + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            String download_url = task.getResult().getDownloadUrl().toString();
                            mUserDatabase.child("profile_image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressupload.dismiss();
                                        Toast.makeText(acceptedEmployee.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(acceptedEmployee.this, "upload failed", Toast.LENGTH_SHORT).show();
                            progressupload.dismiss();
                        }

                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "image cropped unsuccessful", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void moredetails(View view) {

        progressshow=new ProgressDialog(this);
        progressshow.setMessage("Loading");
        progressshow.show();

        if (a)
        {
            hidelayout.setVisibility(View.VISIBLE);
            a=false;
            if (install.equals("first"))
            {
                progressshow.dismiss();
            }
            else
            {
                submit.setVisibility(View.INVISIBLE);
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uuid);

                mUserDatabase.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        progressshow.hide();
                        String fathers = dataSnapshot.child("father").getValue().toString();
                        String mothers = dataSnapshot.child("mother").getValue().toString();
                        String designations = dataSnapshot.child("designation").getValue().toString();
                        String addresss = dataSnapshot.child("address").getValue().toString();
                        String martials=dataSnapshot.child("martial").getValue().toString();
                        String childrens=dataSnapshot.child("children").getValue().toString();
                        father.setText(fathers);
                        mother.setText(mothers);
                        address.setText(addresss);
                        designation.setText(designations);
                        //get the spinner from the xml.
                        spinner2 = (Spinner) findViewById(R.id.spinner2);
                        //create a list of items for the spinner.
                        String[] items1 = new String[]{childrens};
                        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
                        //There are multiple variations of this, but this is the basic variant.
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(acceptedEmployee.this, android.R.layout.simple_spinner_dropdown_item, items1);
                        //set the spinners adapter to the previously created one.
                        spinner2.setAdapter(adapter1);
                        String[] items = new String[]{childrens};
                        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
                        //There are multiple variations of this, but this is the basic variant.
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(acceptedEmployee.this, android.R.layout.simple_spinner_dropdown_item, items);
                        //set the spinners adapter to the previously created one.
                        spinner2.setAdapter(adapter);



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        else
        {
            progressshow.hide();
            hidelayout.setVisibility(View.INVISIBLE);
            a=true;
        }
    }

    public void submitprocess(View view)
    {
        progressshow=new ProgressDialog(this);
        progressshow.setTitle("item saving..");
        progressshow.setMessage("Loading..");
        progressshow.show();
        if (install.equals("first"))
        {
            String fathers=father.getText().toString().trim();
            String mothers=mother.getText().toString().trim();
            String designations=designation.getText().toString().trim();
            String addresss=address.getText().toString().trim();
            if (fathers.isEmpty())
            {
                progressshow.hide();
                father.setError("Empty");
                father.requestFocus();
            }
            else
            {
                if (mothers.isEmpty())
                {
                    progressshow.hide();
                    mother.setError("Empty");
                    mother.requestFocus();
                }
                else {
                    if (designations.isEmpty())
                    {
                        progressshow.hide();
                        designation.setError("Empty");
                        designation.requestFocus();
                    }
                    else
                    {
                        if (addresss.isEmpty())
                        {
                            progressshow.hide();
                            address.setError("Empty");
                            address.requestFocus();
                        }
                        else
                        {
                            progressshow.hide();
                            mUserDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uuid);
                            mUserDatabase.child("father").setValue(fathers);
                            mUserDatabase.child("mother").setValue(mothers);
                            mUserDatabase.child("designation").setValue(designations);
                            mUserDatabase.child("address").setValue(addresss);
                            mUserDatabase.child("install").setValue("second");
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                {
                                    String martial= spinner1.getSelectedItem().toString();
                                    if (martial.isEmpty())
                                    {
                                        spinner1.requestFocus();
                                    }
                                    else {
                                        mUserDatabase.child("martial").setValue(martial);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String children = spinner2.getSelectedItem().toString();
                                    if (children.isEmpty()) {
                                        spinner2.requestFocus();

                                    } else {
                                        mUserDatabase.child("children").setValue(children);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                }

            }
        }



        else
        {}

    }
}
*/


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Math.random;

public class acceptedEmployee extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    TextView NAME, AGE, CONTACT, EMAIL, user_name;
    private static final int Gallery_pick = 1;
    private StorageReference mimageStorage;
    private ProgressDialog pd, progressupload, progressshow;
    ImageView mdisplayImage;
    LinearLayout mrdt2;
    Boolean a = true;
    String install, uuid;
    Button submit, shmr;
    EditText father, mother, designation, address;
    Spinner spinner1, spinner2;
    Toolbar toolbar;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_employee);
        session = new SessionManager(getApplicationContext());

        //creat menubar with logout button
        toolbar = (Toolbar)findViewById(R.id.lgout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EMS");


        mimageStorage = FirebaseStorage.getInstance().getReference();
        user_name = findViewById(R.id.name);
        //get the spinner from the xml.
        spinner1 = (Spinner) findViewById(R.id.marital_status);
        //create a list of items for the spinner.
        String[] items = new String[]{"yes", "no", "Select Marital Status"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner1.setAdapter(adapter);
        spinner1.setSelection(adapter.getCount() - 1);


        //get the spinner from the xml.
        spinner2 = (Spinner) findViewById(R.id.number_of_chill);
        //create a list of items for the spinner.
        String[] items1 = new String[]{"no", "1", "2", "3", "more", "No of children"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        //set the spinners adapter to the previously created one.
        spinner2.setAdapter(adapter1);
        spinner2.setSelection(adapter1.getCount() - 1);


        Intent il = getIntent();
        Bundle b = il.getExtras();
        uuid = b.getString("K1");
        //downcasting the edittext id
        //LinearLayout ll1 = (LinearLayout) findViewById(R.id.rl1);
        father = (EditText) findViewById(R.id.fathers_name);
        mother = (EditText) findViewById(R.id.mothers_name);
        designation = (EditText) findViewById(R.id.designation);
        address = (EditText) findViewById(R.id.address);
        //for button submit
        submit = (Button) findViewById(R.id.btn_submit);
        shmr = (Button) findViewById(R.id.shmr);


        NAME = (TextView) findViewById(R.id.user_name);
        AGE = (TextView) findViewById(R.id.dob);
        CONTACT = (TextView) findViewById(R.id.cntct);
        EMAIL = (TextView) findViewById(R.id.mail);
        mdisplayImage = (ImageView) findViewById(R.id.user_img);
        ImageButton upload_image = (ImageButton) findViewById(R.id.btn_edit);
        mrdt2 = (LinearLayout) findViewById(R.id.mrdt2);


        mAuth = FirebaseAuth.getInstance();

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select_Image"), Gallery_pick);
            }
        });

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //String current_uid=currentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uuid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String contact = dataSnapshot.child("contact").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String image = dataSnapshot.child("profile_image").getValue().toString();
                install = dataSnapshot.child("install").getValue().toString();
                //  Toast.makeText(acceptedEmployee.this, "" + name, Toast.LENGTH_SHORT).show();
                NAME.setText(name);
                user_name.setText(name);
                AGE.setText(age);
                CONTACT.setText(contact);
                EMAIL.setText(email);
                if (!image.equals("image")) {
                    Picasso.with(acceptedEmployee.this).load(image).placeholder(R.drawable.black3).into(mdisplayImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(acceptedEmployee.this, "error!!! Check internet connection", Toast.LENGTH_SHORT).show();

            }
        });

       /* shmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mrdt2.getVisibility() == View.GONE) {
                    mrdt2.setVisibility(View.VISIBLE);
                    shmr.setBackgroundResource(R.drawable.up_arrow);
                } else {
                    mrdt2.setVisibility(View.GONE);
                    shmr.setBackgroundResource(R.drawable.down_arrow);
                }
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser curentuser = mAuth.getCurrentUser();

        if (curentuser == null) {
            sendtostart();
        }
    }

    private void sendtostart() {
        Intent startIntent = new Intent(acceptedEmployee.this, LgsnActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_log_out) {
            session.logoutUser();
            FirebaseAuth.getInstance().signOut();
            sendtostart();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_pick && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(this);
            // Toast.makeText(this, ""+imageUri, Toast.LENGTH_SHORT).show();

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressupload = new ProgressDialog(this);
                progressupload.setTitle("Uploading images..");
                progressupload.setCanceledOnTouchOutside(false);
                progressupload.setMessage("Please wait while we upload the images");
                progressupload.show();

                Uri resultUri = result.getUri();

                StorageReference filepath = mimageStorage.child("profile_images").child(random() + ".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            String download_url = task.getResult().getDownloadUrl().toString();
                            mUserDatabase.child("profile_image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressupload.dismiss();
                                        Toast.makeText(acceptedEmployee.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(acceptedEmployee.this, "upload failed", Toast.LENGTH_SHORT).show();
                            progressupload.dismiss();
                        }

                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "image cropped unsuccessful", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void moredetails(View view) {

        progressshow = new ProgressDialog(this);
        progressshow.setMessage("Loading");
        progressshow.show();

        if (a)
        {
            mrdt2.setVisibility(View.VISIBLE);
            shmr.setBackgroundResource(R.drawable.up_arrow);
            a = false;
            if (install.equals("first"))
            {
                progressshow.dismiss();
            } else {
                submit.setVisibility(View.INVISIBLE);
                mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uuid);

                mUserDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressshow.hide();
                        String fathers = dataSnapshot.child("father").getValue().toString();
                        String mothers = dataSnapshot.child("mother").getValue().toString();
                        String designations = dataSnapshot.child("designation").getValue().toString();
                        String addresss = dataSnapshot.child("address").getValue().toString();
                        String martials = dataSnapshot.child("martial").getValue().toString();
                        String childrens = dataSnapshot.child("children").getValue().toString();
                        father.setText(fathers);
                        mother.setText(mothers);
                        address.setText(addresss);
                        designation.setText(designations);
                        //get the spinner from the xml.
                        //spinner2 = (Spinner) findViewById(R.id.spinner2);
                        //create a list of items for the spinner.
                        String[] items1 = new String[]{childrens};
                        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
                        //There are multiple variations of this, but this is the basic variant.
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(acceptedEmployee.this, android.R.layout.simple_spinner_dropdown_item, items1);
                        //set the spinners adapter to the previously created one.
                        spinner2.setAdapter(adapter1);
                        String[] items = new String[]{childrens};
                        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
                        //There are multiple variations of this, but this is the basic variant.
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(acceptedEmployee.this, android.R.layout.simple_spinner_dropdown_item, items);
                        //set the spinners adapter to the previously created one.
                        spinner2.setAdapter(adapter);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        } else {
            progressshow.hide();
            mrdt2.setVisibility(View.INVISIBLE);
            shmr.setBackgroundResource(R.drawable.down_arrow);
            a = true;
        }
    }

    public void submitprocess(View view) {
        progressshow = new ProgressDialog(this);
        progressshow.setTitle("item saving..");
        progressshow.setMessage("Loading..");
        progressshow.show();
        if (install.equals("first"))
        {
            String fathers = father.getText().toString().trim();
            String mothers = mother.getText().toString().trim();
            String designations = designation.getText().toString().trim();
            String addresss = address.getText().toString().trim();
            if (fathers.isEmpty()) {
                progressshow.hide();
                father.setError("Empty");
                father.requestFocus();
            } else {
                if (mothers.isEmpty()) {
                    progressshow.hide();
                    mother.setError("Empty");
                    mother.requestFocus();
                } else {
                    if (designations.isEmpty()) {
                        progressshow.hide();
                        designation.setError("Empty");
                        designation.requestFocus();
                    } else {
                        if (addresss.isEmpty()) {
                            progressshow.hide();
                            address.setError("Empty");
                            address.requestFocus();
                        } else {
                            progressshow.hide();
                            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uuid);
                            mUserDatabase.child("father").setValue(fathers);
                            mUserDatabase.child("mother").setValue(mothers);
                            mUserDatabase.child("designation").setValue(designations);
                            mUserDatabase.child("address").setValue(addresss);
                            mUserDatabase.child("install").setValue("second");
                            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String martial = spinner1.getSelectedItem().toString();
                                    if (martial.isEmpty()) {
                                        spinner1.requestFocus();
                                    } else {
                                        mUserDatabase.child("martial").setValue(martial);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String children = spinner2.getSelectedItem().toString();
                                    if (children.isEmpty()) {
                                        spinner2.requestFocus();

                                    } else {
                                        mUserDatabase.child("children").setValue(children);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                }

            }
        } else {
        }

    }
}
