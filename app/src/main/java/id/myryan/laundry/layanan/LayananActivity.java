package id.myryan.laundry.layanan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.myryan.laundry.Adapter.AdapterLayanan;
import id.myryan.laundry.R;
import id.myryan.laundry.database.SQLiteHelper;
import id.myryan.laundry.model.ModelLayanan;
import id.myryan.laundry.pelanggan.PelangganActivity;
import id.myryan.laundry.pelanggan.PelangganEditActivity;

import java.util.ArrayList;
import java.util.List;


public class LayananActivity extends AppCompatActivity {

    SQLiteHelper db; // Untuk operasi database SQLite
    Button btnLayAdd; // Tombol untuk tambah pelanggan
    RecyclerView rvLayanan; // RecyclerView untuk menampilkan data pelanggan
    AdapterLayanan adapterLayanan; // Adapter untuk RecyclerView
    ArrayList<ModelLayanan> list; // List untuk menyimpan data pelanggan
    ProgressDialog progressDialog; // Untuk menampilkan loading dialog
    AlphaAnimation btnAnimasi = new AlphaAnimation(1F, 0.5F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setView();
        eventHandling();
        getData();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(btnAnimasi); // Menjalankan animasi tombol
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            ModelLayanan ml = list.get(position); // Ambil data pelanggan dari posisi yang diklik
            // kirim data ke activity lain
            Intent intent= new Intent(LayananActivity.this, LayananEditActivity.class);
            intent.putExtra("id", ml.getId());
            intent.putExtra("tipe", ml.getTipe());
            intent.putExtra("harga", ml.getHarga());
            startActivity(intent);
        }
    };

    private void getData() {
        list.clear(); // Bersihkan list sebelum mengambil data baru
        showMsg(); // Tampilkan loading dialog
        progressDialog.dismiss(); // Sembunyikan loading dialog setelah data berhasil diambil

        try {
            List<ModelLayanan> l = db.getLayanan(); // Ambil data dari database
            if (l.size() > 0) {
                for (ModelLayanan lay : l) {
                    ModelLayanan ml = new ModelLayanan();
                    ml.setId(lay.getId());
                    ml.setTipe(lay.getTipe());
                    ml.setHarga(lay.getHarga());
                    list.add(ml); // Tambahkan data ke list
                }

                adapterLayanan = new AdapterLayanan(this, list);
                adapterLayanan.notifyDataSetChanged();
                rvLayanan.setAdapter(adapterLayanan);

                adapterLayanan.setOnItemClickListener(onClickListener);
            } else {
                Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eventHandling() {
        btnLayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayananActivity.this, LayananAddActivity.class));
            }
        });
    }

    private void setView() {
        db = new SQLiteHelper(this); // Inisialisasi SQLiteHelper
        progressDialog = new ProgressDialog(this); // Inisialisasi ProgressDialog
        btnLayAdd = findViewById(R.id.btnLayAdd); // Temukan tombol tambah pelanggan dari layout
        rvLayanan = findViewById(R.id.rvLayanan); // Temukan RecyclerView dari layout
        list = new ArrayList<>(); // Inisialisasi list untuk menampung data pelanggan

        // Inisialisasi LinearLayoutManager untuk RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL); // Mengatur orientasi menjadi vertikal
        rvLayanan.setHasFixedSize(true); // Optimalkan ukuran RecyclerView
        rvLayanan.setLayoutManager(llm); // Set layout manager ke RecyclerView
    }

    private void showMsg() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Informasi");
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false); // Tidak bisa dibatalkan dengan menekan di luar
        }
        progressDialog.show(); // Tampilkan dialog
    }
}
