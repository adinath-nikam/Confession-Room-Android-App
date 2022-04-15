package com.thehyperprogrammer.confessionroom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {


    View view;
    CircleImageView imageView;
    Button logout_btn;
    TextView textName, textEmail;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser Fuser = FirebaseAuth.getInstance().getCurrentUser();

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile,container,false);
        setHasOptionsMenu(true);

        imageView = view.findViewById(R.id.profile_image_view);
        textName = view.findViewById(R.id.profile_name_textview);
        textEmail = view.findViewById(R.id.profile_email_textview);
        toolbar = view.findViewById(R.id.profile_toolbar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);







        Glide.with(getActivity())
                .load(Fuser.getPhotoUrl())
                .into(imageView);

        textName.setText(Fuser.getDisplayName());
        textEmail.setText(Fuser.getEmail());


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menus, menu);
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){

            case  R.id.menu_logout:
                mAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(),LoginActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
