package fpoly.acount.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        ArrayList<Product> lst = dao.getDS();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvProduct.setLayoutManager(linearLayoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(), lst);
        rcvProduct.setAdapter(adapter);

        return view;

    }
}
