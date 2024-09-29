package id.myryan.laundry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String username;
    CardView laundryCard, layananCard, pelangganCard, promoCard;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inset handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ambil username dari Intent
        username = getIntent().getStringExtra("username");
        Toast.makeText(this, "Welcome, " + username, Toast.LENGTH_SHORT).show();

        // Inisialisasi CardView
        laundryCard = (CardView) findViewById(R.id.laundryCard);
        layananCard = (CardView) findViewById(R.id.layananCard);
        pelangganCard = (CardView) findViewById(R.id.pelangganCard);
        promoCard = (CardView) findViewById(R.id.promoCard);

        // Set onClick listener untuk Laundry Card
        laundryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaundryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set onClick listener untuk Layanan Card
        layananCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LayananActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set onClick listener untuk Pelanggan Card
        pelangganCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PelangganActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set onClick listener untuk Promo Card
        promoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
