package id.myryan.laundry.layanan;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.myryan.laundry.R;
import id.myryan.laundry.database.SQLiteHelper;
import id.myryan.laundry.model.ModelLayanan;

import java.util.UUID;

public class LayananAddActivity extends AppCompatActivity {
    EditText edLayAddTipe, edLayAddHarga;
    Button btnLayAddSimpan, btnLayAddBatal;
    SQLiteHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edLayAddTipe = (EditText) findViewById(R.id.edLayAddTipe);
        edLayAddHarga = (EditText)  findViewById(R.id.edLayAddHarga);
        btnLayAddSimpan = (Button) findViewById(R.id.btnLayAddSimpan);
        btnLayAddBatal = (Button) findViewById(R.id.btnLayAddBatal);

        db = new SQLiteHelper(LayananAddActivity.this);
        btnLayAddSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelLayanan ml = new ModelLayanan();
                String uniqueID = UUID.randomUUID().toString();
                ml.setId(""+uniqueID);
                ml.setTipe(edLayAddTipe.getText().toString());
                ml.setHarga(edLayAddHarga.getText().toString());

                Toast.makeText(LayananAddActivity.this, ""+ml.getId()+ml.getTipe()+ml.getHarga(), Toast.LENGTH_SHORT).show();

                boolean cek = db.insertLayanan(ml);
                Toast.makeText(LayananAddActivity.this, ""+cek, Toast.LENGTH_SHORT).show();
                if (cek == true){
                    Toast.makeText(LayananAddActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LayananAddActivity.this,  LayananActivity.class));
                    finish();
                } else {
                    Toast.makeText(LayananAddActivity.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLayAddBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
