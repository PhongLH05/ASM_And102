package fpoly.acount.asm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.acount.asm.dao.NguoiDungDAO;
import fpoly.acount.asm.util.SendMail;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPass;
    Button btnLogin;
    TextView txtForgot, txtSignup;
    private NguoiDungDAO dao;
    private SendMail sendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);
        txtForgot = findViewById(R.id.txtForgot);

        dao = new NguoiDungDAO(this);
        sendMail = new SendMail();

        btnLogin.setOnClickListener(view -> {
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();

            boolean check = dao.checkLogin(user, pass);

            if (check){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        txtSignup.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        txtForgot.setOnClickListener(view -> {
            showDialogForgot();
        });
    }

    private void showDialogForgot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtReEmail = view.findViewById(R.id.edtreEmail);
        Button btnSend = view.findViewById(R.id.btnSendEmail);
        Button btnCancel = view.findViewById(R.id.btnHuy);

        btnCancel.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

        btnSend.setOnClickListener(view1 -> {
            String email = edtReEmail.getText().toString();
            String matkhau = dao.forGotpassWord(email);
//            Toast.makeText(this, matkhau, Toast.LENGTH_SHORT).show();
            if (matkhau.equals("")){
                Toast.makeText(this, "khong tim thay tai khoan", Toast.LENGTH_SHORT).show();
            }else {
                sendMail.Send(LoginActivity.this, email, "Lay lai mat khau", "Pass: " + matkhau);
                alertDialog.dismiss();
            }
        });
    }
}
