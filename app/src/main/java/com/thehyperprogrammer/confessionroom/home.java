package com.thehyperprogrammer.confessionroom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class home extends Fragment {

    View view;
    RecyclerView recyclerView;
    private Snackbar snackbar;
    FirebaseUser Fuser = FirebaseAuth.getInstance().getCurrentUser();


    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("CONFESSIONS");

    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        snackbar = Snackbar.make(view,"Loading..",Snackbar.LENGTH_INDEFINITE);

//        ViewGroup contentLay = (ViewGroup) snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text).getParent();
//        ProgressBar item = new ProgressBar(getContext());
//
//
//        contentLay.addView(item);












//        Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) snackbar.getView();
//        snack_view.addView(new ProgressBar(getContext()));
//
//        TextView tv = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
//        tv.setTextColor(Color.WHITE);






        snackbar.show();

        FirebaseRecyclerOptions<ConfessionsModel> options = new FirebaseRecyclerOptions.Builder<ConfessionsModel>()
                .setQuery(mRef,ConfessionsModel.class)
                .build();
        FirebaseRecyclerAdapter<ConfessionsModel, ConfessionViewHolder> adapter
                = new FirebaseRecyclerAdapter<ConfessionsModel, ConfessionViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ConfessionViewHolder holder, final int position, @NonNull ConfessionsModel model)
            {
                holder.ConfT.setText(model.getConfTitle());
                holder.ConfB.setText(model.getConfBody());
                holder.ConfD.setText(model.getConfDate());
                holder.ConfTi.setText(model.getConfTime());
                Glide.with(getActivity())
                        .load(Fuser.getPhotoUrl())
                        .into(holder.Confessor_pic);
                holder.ConfUsername.setText(model.getCurrentUser());
                holder.Confess_popup_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(holder.Confess_popup_btn,position);
                    }
                });

                snackbar.dismiss();

            }

            @NonNull
            @Override
            public ConfessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_view, parent, false);
                ConfessionViewHolder viewHolder = new ConfessionViewHolder(view);
                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    public static class ConfessionViewHolder extends RecyclerView.ViewHolder{

        TextView ConfT,ConfB,ConfD,ConfTi,ConfUsername;
        ImageButton Confess_popup_btn;
        CircleImageView Confessor_pic;
        public ConfessionViewHolder(@NonNull View itemView) {
            super(itemView);

            ConfT = itemView.findViewById(R.id.ConfTitle);
            ConfB = itemView.findViewById(R.id.ConfBody);
            ConfD = itemView.findViewById(R.id.ConfDate);
            ConfTi = itemView.findViewById(R.id.ConfTime);
            ConfUsername = itemView.findViewById(R.id.ConfessorUsername);
            Confess_popup_btn = itemView.findViewById(R.id.confess_popup_menu);
            Confessor_pic = itemView.findViewById(R.id.confessor_pic);

        }
    }




    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.confess_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case  R.id.report:
                        Toast.makeText(getContext(), "Report", Toast.LENGTH_SHORT).show();


                    case  R.id.share:
                        Toast.makeText(getContext(), "Share", Toast.LENGTH_SHORT).show();

                }

                return false;

            }
        });
        popup.show();
    }






}
