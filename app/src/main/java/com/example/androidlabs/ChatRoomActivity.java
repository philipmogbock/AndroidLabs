package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;



public class ChatRoomActivity extends AppCompatActivity {
    Button send;
    Button receive;
    EditText textMsg;
    MyListAdapter myAdapter;
    ArrayList<String> elements = new ArrayList<>();
    Message message;
    ListView myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        send = findViewById(R.id.sendBtn);
        receive = findViewById(R.id.receiveBtn);
        textMsg = findViewById(R.id.typeHerePrompt);



        send.setOnClickListener(v -> {
            message = new Message(textMsg.getText().toString(), true);
            elements.add(message.getText());
            myAdapter.notifyDataSetChanged();

        });

        receive.setOnClickListener(v -> {
            message = new Message(textMsg.getText().toString(), false);
            elements.add(message.getText());
            myAdapter.notifyDataSetChanged();
        });


        myList =  findViewById(R.id.myList); //optional cast to (ListView)
        myList.setAdapter(myAdapter = new MyListAdapter());

        //On item long click for delete line option

        myList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this)
                    .setTitle("Do you want to delete this? ")
                    .setMessage("The selected row is : "+ position+
                            "\n The database id is: "+id);

            //yes button
            alertDialogBuilder.setPositiveButton("Yes", (click,arg) ->{
                elements.remove(position);
                myAdapter.notifyDataSetChanged();
            });

            //no button
            alertDialogBuilder.setNegativeButton("No", (click,arg) ->{

            });


            alertDialogBuilder.create().show();
            return true;
        });


    }


    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //don't hardcode the number of elements
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            //when not using databases you can just return 0
            return (long) position;
        }

        @Override //what widget is at row position (Button, textbox, checkbox etc)
        public View getView(int position, View convertView, ViewGroup parent) {

            //get a layout at this position
            LayoutInflater inflater = getLayoutInflater();


            if (message.getSentStatus()) {

                View newView = inflater.inflate(R.layout.send_layout, parent, false);

                TextView name = newView.findViewById(R.id.textMsgSent);
                name.setText(getItem(position).toString());

                newView.findViewById(R.id.senderPic);

                return newView;

            } else {

                View newView = inflater.inflate(R.layout.receive_layout, parent, false);

                TextView name = newView.findViewById(R.id.textMsgReceived);
                name.setText(getItem(position).toString());

                newView.findViewById(R.id.receiverPic);
                return newView;
            }
        }




    }
}
