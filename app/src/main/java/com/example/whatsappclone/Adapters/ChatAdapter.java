package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whatsappclone.Models.MessageModel;
import com.example.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;

    int SENDER_VIEW_TYPE = 0;
    int RECEIVER_VIEW_TYPE = 1;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE) {

            View view = LayoutInflater.from(context).inflate(R.layout.text_sender_item, parent, false);
            return new SenderViewHolder(view);

        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.text_receiver_item, parent, false);
            return new ReceiverViewHolder(view);

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {

            return SENDER_VIEW_TYPE;

        } else {

            return RECEIVER_VIEW_TYPE;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);

        if(holder.getClass() == SenderViewHolder.class) {

            ((SenderViewHolder)holder).sendersText.setText(messageModel.getMessage());
            //((SenderViewHolder)holder).timeSent.setText(messageModel.getTimestamp().toString());

        } else {

            ((ReceiverViewHolder)holder).receivedText.setText(messageModel.getMessage());
            //((ReceiverViewHolder)holder).timeReceived.setText(messageModel.getMessage());

        }

    }

    @Override
    public int getItemCount() {

        return messageModels.size();

    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receivedText, timeReceived;

        public ReceiverViewHolder(@NonNull View itemView) {

            super(itemView);

            receivedText = itemView.findViewById(R.id.receivedText);
            timeReceived = itemView.findViewById(R.id.timeReceived);

        }

    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView sendersText, timeSent;


        public SenderViewHolder(@NonNull View itemView) {

            super(itemView);

            sendersText = itemView.findViewById(R.id.sendersText);
            timeSent = itemView.findViewById(R.id.timeSent);

        }

    }

}
