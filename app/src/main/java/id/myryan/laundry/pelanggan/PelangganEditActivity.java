package id.myryan.laundry.pelanggan;

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
import id.myryan.laundry.model.ModelPelanggan;

public class PelangganEditActivity extends AppCompatActivity {
    // buat string yg digunakan utk menampung data yg dikirimkan dari pelangganactivity
    String id, name, email, hp;

    EditText et_nama, et_email, et_hp;

    Button btnSimpan, btnHapus, btnBatal;

    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelanggan_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new SQLiteHelper(this);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        hp = getIntent().getStringExtra("hp");

        et_nama = (EditText) findViewById(R.id.edPelEditNama);
        et_email = (EditText) findViewById(R.id.edPelEditEmail);
        et_hp = (EditText) findViewById(R.id.edPelEditHp);

        et_nama.setText(name);
        et_email.setText(email);
        et_hp.setText(hp);

        btnSimpan = (Button) findViewById(R.id.btnPelEditSimpan);
        btnHapus = (Button) findViewById(R.id.btnPelEditHapus);
        btnBatal = (Button) findViewById(R.id.btnPelEditBatal);

        btnSimpan.setOnClickListener(v -> updateCustomer());
        btnHapus.setOnClickListener(v -> deleteCustomer());
        btnBatal.setOnClickListener(v -> cancelEdit());

        Toast.makeText(this, "ID: "+id+"\nName : "+name+"\nEmail : "+email+"\nHp : "+hp, Toast.LENGTH_SHORT).show();
    }

    private void updateCustomer() {
        String updatedName = et_nama.getText().toString();
        String updatedEmail = et_email.getText().toString();
        String updatedHp = et_hp.getText().toString();

        // Check if fields are empty
        if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedHp.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the customer in the database
        boolean isUpdated = db.updatePelanggan(id, updatedName, updatedEmail, updatedHp);
        if (isUpdated) {
            Toast.makeText(this, "Customer updated successfully", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,PelangganActivity.class));
            PelangganActivity pa = new PelangganActivity();
            pa.getData();
            finish(); // Close the activity after successful update
        } else {
            Toast.makeText(this, "Failed to update customer", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete customer from the database
    private void deleteCustomer() {
        // Confirmation dialog before deleting
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this customer?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean isDeleted = db.deletePelanggan(id);
                    if (isDeleted) {
                        Toast.makeText(this, "Customer deleted successfully", Toast.LENGTH_SHORT).show();
                        // After successful deletion, restart the PelangganActivity to reflect changes
                        Intent intent = new Intent(this, PelangganActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Close the current activity after deletion
                    } else {
                        Toast.makeText(this, "Failed to delete customer", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void cancelEdit() {
        finish();
    }
}
