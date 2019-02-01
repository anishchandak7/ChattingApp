package com.example.chattingapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {

    private FirebaseAuth auth;
    //private AIService aiService;

    private static final String TAG = "MainActivity";
    private CollectionReference senders_messages = FirebaseFirestore.getInstance().collection("senders_messages");
    ArrayList<String> sender_messages;
    AIConfiguration config;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    String message;
    boolean sender = true,reciever = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        int index = 0;
        layoutManager = new LinearLayoutManager(MainActivity.this);
        config = new AIConfiguration("53d9bdb663814259b46801d0636628ce",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

                senderMessages(index);
                recieverMessages(message);

    }

    public void senderMessages(final int index)
    {
        recyclerView = findViewById(R.id.recyclerview);
        senders_messages.document("messages").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    sender_messages = (ArrayList<String>) documentSnapshot.get("chats");
                    Log.d(TAG, "onSuccess: "+sender_messages.get(0));

                    recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,sender_messages.get(index));
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    int i = index;
                    if(!sender_messages.get(i).isEmpty()) {
                        setSenderMessage(sender_messages.get(i));
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Message Ended",Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        sender=false;
        reciever=true;
    }

    private void setSenderMessage(String s) {
        message = s;
    }

    public void recieverMessages(String s)
    {
        final AIService aiService = AIService.getService(this, config);
        aiService.setListener(this);
        aiService.startListening();
        final AIDataService aiDataService = new AIDataService(this,config);
        final AIRequest aiRequest = new AIRequest();
        aiRequest.setQuery(s);
        new  AsyncTask<AIRequest,Void, AIResponse>()
        {

            @SuppressLint("WrongThread")
            @Override
            protected AIResponse doInBackground(AIRequest... aiRequests) {
                if(aiRequests!=null) {
                    final AIRequest request = aiRequests[0];
                    try {
                        final AIResponse response = aiDataService.request(aiRequest);
                        return response;
                    } catch (AIServiceException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d(TAG,"problem in doinbackground");
                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if (aiResponse != null) {

                    Result result = aiResponse.getResult();
                    RecyclerViewAdapter recyclerViewAdapter2 = new RecyclerViewAdapter(MainActivity.this,result.getMetadata().getIntentName());
                    recyclerView.setAdapter(recyclerViewAdapter2);
                    recyclerView.setLayoutManager(layoutManager);
                    Log.d("inside_postexecute", result.getMetadata().getIntentName().toString());
                }
            }
        }.execute(aiRequest);
        reciever=false;
        sender=true;
        Thread.currentThread().interrupt();
    }
    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
