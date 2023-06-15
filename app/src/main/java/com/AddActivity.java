package com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.crudoperation.MainActivity;
import com.e.crudoperation.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText edtName,edtJobRole,edtDegree,edtUrl;
    Button btnAdd,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtName=findViewById(R.id.edt_names);
        edtJobRole=findViewById(R.id.edt_roles);
        edtDegree=findViewById(R.id.edt_degrees);
        edtUrl=findViewById(R.id.edt_urls);
        btnAdd=findViewById(R.id.add);
        back=findViewById(R.id.back);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
                clearalldata();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void insertData()
    {

        Map<String,Object> map = new HashMap<>();
        map.put("name",edtName.getText().toString());
        map.put("role",edtJobRole.getText().toString());
        map.put("degree",edtDegree.getText().toString());
        map.put("url",edtUrl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("employees")
                .push().setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(AddActivity.this,"Data Inserted Sucessfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Data Not Added",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearalldata()
    {
        edtName.setText("");
        edtJobRole.setText("");
        edtDegree.setText("");
        edtUrl.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddActivity.this,MainActivity.class));
        finish();
    }
}