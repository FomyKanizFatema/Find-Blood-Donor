package com.aiub.kfomy.findblooddonor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
   private EditText inputName,inputEmail,inputPhone,inputAge,inputPass;
   private  Button btnReg;
   private Spinner spinner;
   private  String name,email,phone,age,pass,bloodGroup,gender;
   private RadioGroup radioGroupGender;
   private RadioButton checkedButton;
   private  Donor donor;
   private FirebaseDatabase database;
   private DatabaseReference dbRef;
   private FirebaseAuth auth;
   ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        database=FirebaseDatabase.getInstance();
        dbRef=database.getReference("donors");
        auth=FirebaseAuth.getInstance();

        donor=new Donor();

        progressBar=findViewById(R.id.progressBar);
        inputName=findViewById(R.id.name);
        inputEmail=findViewById(R.id.email);
        inputPhone=findViewById(R.id.phone);
        inputAge=findViewById(R.id.age);
        inputPass=findViewById(R.id.pass);
        btnReg=findViewById(R.id.btnReg);
        spinner=findViewById(R.id.blood_group_spinner);
        radioGroupGender=findViewById(R.id.gender);
        checkedButton=null;
        progressBar.setVisibility(View.GONE);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedRadioID=radioGroup.getCheckedRadioButtonId();
                checkedButton=findViewById(selectedRadioID);
                gender=checkedButton.getText().toString().trim();
            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                bloodGroup=adapterView.getItemAtPosition(pos).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bloodGroup=adapterView.getItemAtPosition(0).toString().trim();

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              registerDonor();

            }
        });





    }
    private String getName(){
        return inputName.getText().toString().trim();
    }
    private String getEmail(){
        return inputEmail.getText().toString().trim();
    }
    private String getPhone(){
        return inputPhone.getText().toString().trim();
    }
    private String getAge(){
        return inputAge.getText().toString().trim();
    }
    private String getBloodGroup(){

        return bloodGroup;
    }
    private String getGender(){

        return gender;
    }
    private String getPass(){
        return inputPass.getText().toString();
    }


    public void registerDonor(){

        if(TextUtils.isEmpty(getName())||TextUtils.isEmpty(getEmail())||TextUtils.isEmpty(getPhone())
                ||TextUtils.isEmpty(getAge())||TextUtils.isEmpty(getPass())||TextUtils.isEmpty(getBloodGroup())
                ||TextUtils.isEmpty(getGender())){
            Toast.makeText(RegistrationActivity.this,"No Field Can Be Empty !",Toast.LENGTH_LONG).show();


        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            auth.createUserWithEmailAndPassword(getEmail(),getPass()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            donor = new Donor(getName(), getEmail(), getPhone(), getAge(), getPass(), getBloodGroup(), getGender());
                            String donorId=auth.getCurrentUser().getUid();
                            dbRef.child(donorId).setValue(donor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task2.isSuccessful()){

                                        Toast.makeText(RegistrationActivity.this,"Registration Successful, Please Login",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                    }
                                    else {

                                        Toast.makeText(RegistrationActivity.this,task2.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }

                            });

                        }

                        else{

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
            });


        }


    }


}
