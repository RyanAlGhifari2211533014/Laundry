package id.myryan.laundry.layanan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.myryan.laundry.R;
import id.myryan.laundry.database.SQLiteHelper;

public class LayananEditActivity extends AppCompatActivity {

    private String id, tipe, harga;
    private EditText et_tipe, et_harga;
    private Button btnSimpan, btnHapus, btnBatal;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan_edit);

        // Untuk menangani padding sistem
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new SQLiteHelper(this);

        id = getIntent().getStringExtra("id");
        tipe = getIntent().getStringExtra("tipe");
        harga = getIntent().getStringExtra("harga");

        et_tipe = findViewById(R.id.edLayEditTipe);
        et_harga = findViewById(R.id.edLayEditHarga);

        et_tipe.setText(tipe);
        et_harga.setText(harga);

        btnSimpan = findViewById(R.id.btnLayEditSimpan);
        btnHapus = findViewById(R.id.btnLayEditHapus);
        btnBatal = findViewById(R.id.btnLayEditBatal);

        btnSimpan.setOnClickListener(v -> updateLayanan());
        btnHapus.setOnClickListener(v -> deleteLayanan());
        btnBatal.setOnClickListener(v -> cancelEdit());

        Toast.makeText(this, "ID: " + id + "\nTipe: " + tipe + "\nHarga: " + harga, Toast.LENGTH_SHORT).show();
    }

    private void updateLayanan() {
        String updatedTipe = et_tipe.getText().toString();
        String updatedHarga = et_harga.getText().toString();

        if (updatedTipe.isEmpty() || updatedHarga.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = db.updateLayanan(id, updatedTipe, updatedHarga);
        if (isUpdated) {
            Toast.makeText(this, "Layanan updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LayananActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update layanan", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteLayanan() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this layanan?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean isDeleted = db.deleteLayanan(id);
                    if (isDeleted) {
                        Toast.makeText(this, "Layanan deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LayananActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to delete layanan", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void cancelEdit() {
        finish();
    }
}
