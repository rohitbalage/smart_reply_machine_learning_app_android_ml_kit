package com.example.imageclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView resultTv;
    Button senderBtn, receivedBtn;
    EditText senderEd, receiverEd;
    List<TextMessage> conversation;

    SmartReplyGenerator smartReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.textView);
        senderBtn = findViewById(R.id.button);
        receivedBtn = findViewById(R.id.button2);
        senderEd = findViewById(R.id.editTextTextPersonName);
        receiverEd = findViewById(R.id.editTextTextPersonName2);
        conversation = new ArrayList<>();


        senderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversation.add(TextMessage.createForLocalUser(
                        senderEd.getText().toString(), System.currentTimeMillis()));
                resultTv.setText("");
                senderEd.setText("");


                smartReply.suggestReplies(conversation)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                resultTv.setText("");
                                SmartReplySuggestionResult result = (SmartReplySuggestionResult) o;
                                if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {

                                } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                    // Task completed successfully
                                    // ....

                                    for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                                        String replyText = suggestion.getText();
                                        resultTv.append(replyText + "\n");
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        receivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversation.add(TextMessage.createForRemoteUser(
                        receiverEd.getText().toString(), System.currentTimeMillis(), "1"));
                resultTv.setText("");
                receiverEd.setText("");

                smartReply.suggestReplies(conversation)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                resultTv.setText("");
                                SmartReplySuggestionResult result = (SmartReplySuggestionResult) o;
                                if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {

                                } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                    // Task completed successfully
                                    // ....

                                    for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                                        String replyText = suggestion.getText();
                                        resultTv.append(replyText+"\n");
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        // Load Smart reply model
        smartReply = SmartReply.getClient();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
