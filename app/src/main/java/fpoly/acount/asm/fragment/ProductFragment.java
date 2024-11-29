package fpoly.acount.asm.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.acount.asm.R;
import fpoly.acount.asm.adapter.ProductAdapter;
import fpoly.acount.asm.dao.SanPhamDAO;
import fpoly.acount.asm.model.Product;

public class ProductFragment extends Fragment {
    private RecyclerView rcvProduct;
    private FloatingActionButton btnAdd;
    private SanPhamDAO dao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        rcvProduct = view.findViewById(R.id.rcvProduct);
        btnAdd = view.findViewById(R.id.btnAdd);

        dao = new SanPhamDAO(getContext());
        loadData();

        btnAdd.setOnClickListener(view1 -> {
           showDialogAdd();
        });

        return view;

    }


    private void loadData(){
        ArrayList<Product> lst = dao.getDS();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(), lst);
        rcvProduct.setAdapter(adapter);
    }

    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        EditText edtPrName, edtPrPrice, edtPrQuantity;
        Button btnThem, btnHuy;

        edtPrName = view.findViewById(R.id.edtPrName);
        edtPrPrice = view.findViewById(R.id.PrPrice);
        edtPrQuantity = view.findViewById(R.id.PrQuantity);
        btnThem = view.findViewById(R.id.btnThem);
        btnHuy = view.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(view1 -> {
            String tensp = edtPrName.getText().toString();
            String giasp = edtPrPrice.getText().toString();
            String slsp = edtPrQuantity.getText().toString();

            if (tensp.isEmpty() || giasp.isEmpty() || slsp.isEmpty()){
                Toast.makeText(getContext(), "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
            }else {
                Product product = new Product(tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                boolean check = dao.addProdduct(product);
                if (check){
                    Toast.makeText(getContext(), "Them san pham moi thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Them san pham khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnHuy.setOnClickListener(view1 -> {
            alertDialog.dismiss();
        });

    }
}
