package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsappclone.ChatDetails;
import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    ArrayList<Users> usersList;
    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public UsersAdapter(ArrayList<Users> usersList, Context context) {

        this.usersList = usersList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Users users = usersList.get(position);
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.ic_user).into(holder.imageView);
        holder.etUserName.setText(users.getUserName());

        database.getReference()
                .child("Chats")
                .child(FirebaseAuth.getInstance().getUid() + users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChildren())

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            holder.tvLastMessage.setText(dataSnapshot.child("message").getValue().toString());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatDetails.class);
                intent.putExtra("userId", users.getUserId());
                intent.putExtra("userName", users.getUserName());
                intent.putExtra("profilePic", users.getProfilePic());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        if(usersList != null) {

            return usersList.size();

        }

        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView etUserName, tvLastMessage;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            etUserName = itemView.findViewById(R.id.tvUserName);

        }
    }

}
