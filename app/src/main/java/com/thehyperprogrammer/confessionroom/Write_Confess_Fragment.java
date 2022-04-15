package com.thehyperprogrammer.confessionroom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class Write_Confess_Fragment extends Fragment {

    View view;
    String ConfessionTitleStr, ConfessionBodyStr,CURRENTDATE,CURRENTTIME;
    EditText ConfessionTitle;
    EditText ConfessionBody;
    String currentUser;
    Switch anonymousSwitch;
    Toolbar toolbar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser Fuser = FirebaseAuth.getInstance().getCurrentUser();


    public Write_Confess_Fragment() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String currentUid = Fuser.getUid();

        view = inflater.inflate(R.layout.fragment_write__confess_,container,false);
        ConfessionTitle = view.findViewById(R.id.confessionETTitle);
        ConfessionBody = view.findViewById(R.id.confessionETBody);
        ImageButton WriteConfBtn = view.findViewById(R.id.writeConfession_btn);
        anonymousSwitch = view.findViewById(R.id.AnonimitySwitch);
        toolbar = view.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);







        WriteConfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (anonymousSwitch.isChecked()){
                    currentUser = "Anonymous";

                }
                else{
                    currentUser = Fuser.getDisplayName();
                }

                ConfessionTitleStr = ConfessionTitle.getText().toString();
                ConfessionBodyStr = ConfessionBody.getText().toString();

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                CURRENTDATE = currentDateFormat.format(calForDate.getTime());

                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
                CURRENTTIME = currentTimeFormat.format(calForTime.getTime());

                if(ConfessionTitleStr.isEmpty() || ConfessionBodyStr.isEmpty()){

                    if(ConfessionTitleStr.isEmpty()){
                        ConfessionTitle.setError("please give title for your confession");
                        ConfessionTitle.requestFocus();
                    }
                    if(ConfessionBodyStr.isEmpty()){
                        ConfessionBody.setError("please write confession");
                        ConfessionBody.requestFocus();
                    }
                }
                else{
                    UploadConf(ConfessionTitleStr,ConfessionBodyStr,CURRENTDATE,CURRENTTIME,currentUser,currentUid);
                }







            }

            private void UploadConf(String confessionTitleStr, String confessionBodyStr, String currentdate, String currenttime, String currentuser,String CurrentUid) {

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("confTitle",confessionTitleStr);
                hashMap.put("confBody",confessionBodyStr);
                hashMap.put("ConfDate",currentdate);
                hashMap.put("ConfTime",currenttime);
                hashMap.put("CurrentUser",currentuser);
                hashMap.put("CurrentUserID",CurrentUid);

                DatabaseReference ConfRef = FirebaseDatabase.getInstance().getReference();

                ConfRef.child("CONFESSIONS").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Snackbar.make(view, "Confession Successful", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                });


            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        TextView Confessor_name = view.findViewById(R.id.confessor_username);
        TextView Confessor_email = view.findViewById(R.id.confessor_email);
        ImageView Confessor_pic = view.findViewById(R.id.confessor_pic);

        Confessor_name.setText(Fuser.getDisplayName());
        Confessor_email.setText(Fuser.getEmail());
        Glide.with(getActivity())
                .load(Fuser.getPhotoUrl())
                .into(Confessor_pic);

    }
}
