package com.example.andrea.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends Activity {

//some stuff
    EditText nameText1, phoneText1, emailText1, addressText1;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    ImageView imgView;
    Uri imageUri = Uri.parse("android.resource://com.example.andrea.myapplication/drawable/index.png");
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText1 = (EditText)findViewById(R.id.nameText);
        phoneText1 = (EditText)findViewById(R.id.phoneText);
        emailText1 = (EditText)findViewById(R.id.emailText);
        addressText1 = (EditText)findViewById(R.id.addressText);
        final Button addbtn = (Button)findViewById(R.id.button);
        contactListView = (ListView) findViewById(R.id.listView);
        imgView = (ImageView)findViewById(R.id.imageView);
        dbHandler = new DatabaseHandler(getApplicationContext());
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(nameText1.getText()), String.valueOf(phoneText1.getText()), String.valueOf(emailText1.getText()), String.valueOf(addressText1.getText()), imageUri);
                dbHandler.createContact(contact);
                Contacts.add(contact);
                //    Contacts.add(new Contact(0,nameText1.getText().toString(), phoneText1.getText().toString(), emailText1.getText().toString(), addressText1.getText().toString(), imageUri));
                populate_list();
                Toast.makeText(getApplicationContext(), "Contact created under name: " + nameText1.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.creator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.list);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        nameText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addbtn.setEnabled(!nameText1.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select contact image"),1);
            }
        });

        List<Contact> addableContacts = dbHandler.getAllContacts();
        int contactCount = dbHandler.getContactsCount();

        for(int i = 0; i < contactCount; i++){
            Contacts.add(addableContacts.get(i));
        }
        if(!addableContacts.isEmpty())
            populate_list();

    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if(reqCode == 1){
                imageUri = data.getData();
                imgView.setImageURI(data.getData());
            }
        }
    }

    private class ContactListAdapter extends ArrayAdapter<Contact>{
        public ContactListAdapter(){
            super(MainActivity.this, R.layout.list_viewitem, Contacts);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.list_viewitem, parent, false);
            }
            Contact currentContact = Contacts.get(position);
            TextView namecon = (TextView)view.findViewById(R.id.textView);
            namecon.setText(currentContact.get_name());
            TextView phone = (TextView) view.findViewById(R.id.textView2);
            phone.setText(currentContact.get_phone());
            TextView email = (TextView) view.findViewById(R.id.textView3);
            email.setText(currentContact.get_email());
            TextView address = (TextView) view.findViewById(R.id.textView4);
            address.setText(currentContact.get_address());
            ImageView imageView = (ImageView)view.findViewById(R.id.imgList);
            imageView.setImageURI(currentContact.get_image());
            return view;
        }
    }


    private void populate_list(){
        ArrayAdapter<Contact>  adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
