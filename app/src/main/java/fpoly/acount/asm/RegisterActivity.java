package fpoly.acount.asm;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
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

        View decoreView = getWindow().getDecorView();
        decoreView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsets onApplyWindowInsets(@NonNull View view, @NonNull WindowInsets windowInsets) {
                int left = windowInsets.getSystemWindowInsetLeft();
                int top = windowInsets.getSystemWindowInsetTop();
                int bottom = windowInsets.getSystemWindowInsetBottom();
                int right = windowInsets.getSystemWindowInsetRight();
                view.setPadding(left, top, right, bottom);
                return windowInsets.consumeSystemWindowInsets();
            }
        });


        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");



        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        edtFullname = findViewById(R.id.edtFullname);
        btnRegister = findViewById(R.id.btnRegister);
        dao = new NguoiDungDAO(this);

        btnRegister.setOnClickListener(view -> {
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();
            String fullname = edtFullname.getText().toString();

            boolean check = dao.register(user, pass, fullname);
            if (check){
                Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "that bai", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
