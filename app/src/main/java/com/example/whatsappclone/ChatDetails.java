package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.Models.MessageModel;
import com.example.whatsappclone.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetails extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        final String senderId = mAuth.getUid();
        String receiverId = intent.getStringExtra("userId");
        String profilePic = intent.getStringExtra("profilePic");
        String userName = intent.getStringExtra("userName");

        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).into(binding.profileImage);

        binding.backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChatDetails.this, MainActivity.class));

            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final ChatAdapter adapter = new ChatAdapter(messageModels, this);

        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);

        final String sendersRoom = senderId + receiverId;
        final String receiversRoom = receiverId + senderId;

        binding.send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String message = binding.messageText.getText().toString();

                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());

                binding.messageText.setText("");

                database.getReference().child("Chats")
                        .child(sendersRoom)
                        .push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        database.getReference().child("Chats")
                                .child(receiversRoom)
                                .push().setValue(model);

                    }
                });

            }
        });

    }
}