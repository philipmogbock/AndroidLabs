package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;


public class ChatRoomActivity extends AppCompatActivity {
    Button send;
    Button receive;
    EditText textMsg;
    MyListAdapter myAdapter;
//    ArrayList<String> messageList = new ArrayList<>();
    ArrayList<Message> messageList = new ArrayList<>();
    Message message;
    ListView myList;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        send = findViewById(R.id.sendBtn);
        receive = findViewById(R.id.receiveBtn);
        textMsg = findViewById(R.id.typeHerePrompt);

        loadDataFromDatabase();



        send.setOnClickListener(v -> {
            message = new Message(textMsg.getText().toString(), true, send.getId());
            messageList.add(message);

            //insert data into db
            ContentValues cv= new ContentValues();
            cv.put(MyOpener.COL_MSG, textMsg.getText().toString() );
            cv.put(MyOpener.COL_IS_SENT, 1);
            long id= db.insert(MyOpener.TABLE_NAME, null, cv);
            textMsg.setText("");
            myAdapter.notifyDataSetChanged();

        });

        receive.setOnClickListener(v -> {
            //create message object
            message = new Message(textMsg.getText().toString(), false,receive.getId());
            //add message to array list
            messageList.add(message);

            //insert data into db
            ContentValues cv= new ContentValues();
            cv.put(MyOpener.COL_MSG, textMsg.getText().toString() );
            cv.put(MyOpener.COL_IS_SENT, 0);
            long id= db.insert(MyOpener.TABLE_NAME, null, cv);
            textMsg.setText("");
            myAdapter.notifyDataSetChanged();
        });

        myList =  findViewById(R.id.myList); //optional cast to (ListView)
        myList.setAdapter(myAdapter = new MyListAdapter());

        //On item long click for delete line option

        myList.setOnItemLongClickListener((parent, view, position, id) -> {
            //store selected message
            Message selectedMessage = messageList.get(position);
            //build Alert Dialog
            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this)
                    .setTitle("Do you want to delete this? ")
                    .setMessage("The selected row is : "+ position+
                            "\n The database id is: "+id);

            //yes button
            alertDialogBuilder.setPositiveButton("Yes", (click,arg) ->{
                //remove message from arraylist
                messageList.remove(position);
                myAdapter.notifyDataSetChanged();
                //delete message for database
                deleteMessage(selectedMessage);
            });

            //no button
            alertDialogBuilder.setNegativeButton("No", (click,arg) ->{

            });

            alertDialogBuilder.create().show();
            return true; });
    }


    protected class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //don't hardcode the number of elements
            return messageList.size();
        }

        @Override
        public Message getItem(int position) {
            return messageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            //when not using databases you can just return 0
//            return (long) position;
            return  getItem(position).getId();
        }

        @Override //what widget is at row position (Button, textbox, checkbox etc)
        public View getView(int position, View convertView, ViewGroup parent) {

            //get a layout at this position
            LayoutInflater inflater = getLayoutInflater();


            if (getItem(position).getSentStatus()) {

                View newView = inflater.inflate(R.layout.send_layout, parent, false);

                TextView name = newView.findViewById(R.id.textMsgSent);
                name.setText(getItem(position).getText());

                newView.findViewById(R.id.senderPic);

                return newView;

            } else {

                View newView = inflater.inflate(R.layout.receive_layout, parent, false);

                TextView name = newView.findViewById(R.id.textMsgReceived);
                name.setText(getItem(position).getText());

                newView.findViewById(R.id.receiverPic);
                return newView;
            }
        }




    }



    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID,MyOpener.COL_MSG, MyOpener.COL_IS_SENT };
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MSG);
        int isSentColIndex = results.getColumnIndex(MyOpener.COL_IS_SENT);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String msg = results.getString(messageColumnIndex);
            int sent = results.getInt(isSentColIndex);
            long id = results.getLong(idColIndex);

            //convert sent to Boolean
            Boolean real_Sent;
                if(sent==0){
                    real_Sent= false;
                }
                else{
                    real_Sent= true;
                }
                //add the new Message to the array list
                messageList.add(new Message(msg,real_Sent, id));
        }
        printCursor(results, db.getVersion());
    }

    protected void deleteMessage(Message m)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(m.getId())});
    }


    private void printCursor(Cursor c, int version) {

//        Log.i("Database details","Database Version Number: "+ c.getString(version)+
//                "\nNumber of Columns in cursor: "+c.getString(c.getColumnCount())+
//                "\nName of Columns in cursor: "+Arrays.toString(c.getColumnNames())+
//                "\nNumber of Rows in cursor: " + c.getString(c.getCount())
//        );

        Log.i("Database details", "Database Version Number: " + version +
                "\nNumber of Columns in cursor: " + c.getColumnCount() +
                "\nName of Columns in cursor: " + Arrays.toString(c.getColumnNames()) +
                "\nNumber of Rows in cursor: " + c.getCount()
        );

//        print out each row of results

        //get the index of each column
        int idColIndex = (c.getColumnIndex(MyOpener.COL_ID));
        int msgColIndex = (c.getColumnIndex(MyOpener.COL_MSG));
        int isSentColIndex = (c.getColumnIndex(MyOpener.COL_IS_SENT));
        StringBuilder resultSB = new StringBuilder("Results of each row in Cursor");

        //loop through
        if (c.moveToNext()) {
            do {
//        while (c.moveToNext()){
                //get the values at those indexes
                int isSent = c.getInt(isSentColIndex);
                String msg = c.getString(msgColIndex);
                long id = c.getLong(idColIndex);

                //append data to the string builder object
                resultSB.append("\nRow : ").append(c.getPosition())
                        .append("\nID: ").append(id)
                        .append("\nMessage: ").append(msg)
                        .append("\nIs Sent Status (0= received, 1=sent): ").append(isSent)
                        .append("");


            } while (c.moveToNext());
            }

//       print info to the log screen
        Log.i("Results of each row", resultSB.toString());

    }


}
