package fpoly.acount.asm;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

import fpoly.acount.asm.dao.NguoiDungDAO;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUser, edtPass, edtFullname;
    Button btnRegister;
     NguoiDungDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Toolbar myToolbar = findViewById(R.id.myToolbar);
//        setSupportActionBar(myToolbar);
//
//
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        edtFullname = findViewById(R.id.edtFullname);

//        btnRegister.setOnClickListener(view -> {
//            String user = edtUser.getText().toString();
//            String pass = edtPass.getText().toString();
//            String fullname = edtFullname.getText().toString();
//
//            boolean check = dao.register(user, pass, fullname);
//            if (check){
//                Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });



    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
}
