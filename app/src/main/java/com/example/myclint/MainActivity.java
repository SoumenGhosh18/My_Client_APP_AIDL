package com.example.myclint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.server.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent();
        ComponentName name = new ComponentName("com.example.server","com.example.server.MyService");
        intent.setComponent(name);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    public void start(View view) throws RemoteException {
        if(iMyAidlInterface!=null) {
            EditText editText = findViewById(R.id.edittext);

            TextView view1 = findViewById(R.id.textview);
            view1.setText(iMyAidlInterface.send(editText.getText().toString()));
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface =  IMyAidlInterface.Stub.asInterface(iBinder);
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iMyAidlInterface = null;
            Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_SHORT).show();
        }
    };
}