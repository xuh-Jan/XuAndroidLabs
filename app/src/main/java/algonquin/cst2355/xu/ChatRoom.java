package algonquin.cst2355.xu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2355.xu.databinding.ActivityChatRoomBinding;
import algonquin.cst2355.xu.databinding.SentMessageBinding;
import algonquin.cst2355.xu.databinding.ReceiveMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        ArrayList<ChatMessage> messages = chatModel.messages.getValue();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>()); // Initialize if not set
        }

        ArrayList<ChatMessage> finalMessages = messages;

        binding.sendButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage sentMessage = new ChatMessage(messageText, currentDateandTime, true);

            finalMessages.add(sentMessage);
            binding.textInput.setText("");

            setupRecyclerView(finalMessages);
        });

        binding.receiveButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage receivedMessage = new ChatMessage(messageText, currentDateandTime, false);

            finalMessages.add(receivedMessage);
            binding.textInput.setText("");

            setupRecyclerView(finalMessages);
        });
    }

    private void setupRecyclerView(ArrayList<ChatMessage> finalMessages) {
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding sentBinding = SentMessageBinding.inflate(getLayoutInflater(),parent,false);
                    return new MyRowHolder(sentBinding.getRoot(), sentBinding.message, sentBinding.time);
                } else {
                    ReceiveMessageBinding receiveBinding = ReceiveMessageBinding.inflate(getLayoutInflater(),parent,false);
                    return new MyRowHolder(receiveBinding.getRoot(), receiveBinding.message, receiveBinding.time);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = finalMessages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return finalMessages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (finalMessages.get(position).isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView, TextView messageText, TextView timeText) {
            super(itemView);
            this.messageText = messageText;
            this.timeText = timeText;
        }
    }
}
