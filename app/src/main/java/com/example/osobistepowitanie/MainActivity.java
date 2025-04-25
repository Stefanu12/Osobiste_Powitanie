package com.example.osobistepowitanie;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "kanal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        EditText editText = findViewById(R.id.editTextImie);
        Button but1 = findViewById(R.id.buttonPowitanie);
        but1.setOnClickListener(v -> {
            String imie = editText.getText().toString().trim();
            if (imie.isEmpty()) {
                AlertDialog();
            } else {
                AlertDialog2(imie);
            }
        });
    }
    private void AlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Błąd");
        builder.setMessage("Proszę wpisać swoje imię!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }
    private void AlertDialog2(String imie){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Potwierdzenie");
        builder.setMessage("Cześć " + imie + " Czy chcesz otrzymać powiadomienie powitalne?");

        builder.setPositiveButton("Tak, poproszę", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Powiadomienie zostało wysłane!",Toast.LENGTH_LONG).show();
                pow(imie);
            }
        });
        builder.setNegativeButton("Nie, dziękuję", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"Rozumiem. Nie wysyłam powiadomienia.",Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "kanal",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void pow(String imie) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Witaj!")
                .setContentText("Miło Cię widzieć," + imie)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        pokaz(builder);
    }

    private void pokaz(NotificationCompat.Builder builder) {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }

}