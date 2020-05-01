package com.example.java_chat_app;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder( View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textMessage);
        }
    }
    List <MessageResponse> messageResponseList;
    public MessageAdapter(List <MessageResponse> messageResponseList){
        this.messageResponseList = messageResponseList;

    }

    @Override
    public int getItemViewType(int position) {
       if(messageResponseList.get(position).getCheckMe()) {
           return R.layout.my_bubble;
       }
       return R.layout.bot_bubble;

   }

    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.CustomViewHolder holder, int position) {
        holder.textView.setText(messageResponseList.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        return messageResponseList.size();
    }
}
