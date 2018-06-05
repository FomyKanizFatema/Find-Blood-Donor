package com.aiub.kfomy.findblooddonor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView textView;
    FirebaseAuth auth;
    DatabaseReference dbRef;
    ListView listView;
    ArrayList<Donor> donorList;
    String name[],bg[],phone[];
    Spinner spinner_age,spinner_blood_group,spinner_area;
    String filterKeyAge,filterKeyBG,filterKeyArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbRef= FirebaseDatabase.getInstance().getReference("donors");
        auth=FirebaseAuth.getInstance();
        listView=findViewById(R.id.list_view);
        donorList=new ArrayList<>();
        final CustomAdapter customAdapter=new CustomAdapter();
        listView.setAdapter(customAdapter);
        spinner_age=findViewById(R.id.spinner_age);
        spinner_area=findViewById(R.id.spinner_area);
        spinner_blood_group=findViewById(R.id.spinner_blood_group);

        ArrayAdapter<CharSequence> adapter_blood=ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        adapter_blood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(adapter_blood);

        ArrayAdapter<CharSequence> adapter_age=ArrayAdapter.createFromResource(this,R.array.age_group,android.R.layout.simple_spinner_item);
        adapter_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(adapter_age);

        ArrayAdapter<CharSequence> adapter_area=ArrayAdapter.createFromResource(this,R.array.location,android.R.layout.simple_spinner_item);
        adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_area.setAdapter(adapter_area);

        spinner_blood_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterKeyBG=adapterView.getItemAtPosition(i).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filterKeyBG=adapterView.getItemAtPosition(1).toString().trim();
            }
        });


        spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterKeyAge=adapterView.getItemAtPosition(i).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filterKeyAge=adapterView.getItemAtPosition(1).toString().trim();
            }
        });

        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterKeyArea=adapterView.getItemAtPosition(i).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filterKeyArea=adapterView.getItemAtPosition(1).toString().trim();
            }
        });


        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Donor donor=dataSnapshot.getValue(Donor.class);
                donorList.add(donor);
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return donorList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view=getLayoutInflater().inflate(R.layout.list_layout,null);
            TextView textViewName=view.findViewById(R.id.listName);
            TextView textViewBloodGroup=view.findViewById(R.id.listLocation);
            TextView textViewPhone=view.findViewById(R.id.listPhone);

            textViewName.setText(donorList.get(i).getName());
            textViewBloodGroup.setText(donorList.get(i).getBloodGroup());
            textViewPhone.setText(donorList.get(i).getPhone());
            return view;
        }
    }
}
