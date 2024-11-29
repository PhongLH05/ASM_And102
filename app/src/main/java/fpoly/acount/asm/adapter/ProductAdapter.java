package fpoly.acount.asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import fpoly.acount.asm.R;
import fpoly.acount.asm.dao.SanPhamDAO;
import fpoly.acount.asm.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Product> lst;
    SanPhamDAO dao;

    public ProductAdapter(Context context, ArrayList<Product> lst) {
        this.context = context;
        this.lst = lst;
        dao = new SanPhamDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(lst.get(position).getTensp());

        NumberFormat format = new DecimalFormat("#,###");
        double so = lst.get(position).getGiaban();
        String formater = format.format(so);

        holder.txtPrice.setText(formater + "VND");
        holder.txtQuantity.setText("SL: " + lst.get(position).getSoluong());

        holder.txtDelete.setOnClickListener(view -> {
            showDialogDel(lst.get(holder.getAdapterPosition()).getTensp(), lst.get(holder.getAdapterPosition()).getMasp());
        });

        holder.txtEdit.setOnClickListener(view -> {
            showDialogUpadte(lst.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtPrice, txtQuantity, txtEdit, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtDelete = itemView.findViewById(R.id.txtDelete);
        }
    }

    private void showDialogUpadte(Product product){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view1 = inflater.inflate(R.layout.edit_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view1);
        EditText edtPrName, edtPrPrice, edtPrQuantity;
        Button btnThem, btnHuy;
        edtPrName = view1.findViewById(R.id.edtPrName);
        edtPrPrice = view1.findViewById(R.id.PrPrice);
        edtPrQuantity = view1.findViewById(R.id.PrQuantity);
        btnThem = view1.findViewById(R.id.btnThem);
        btnHuy = view1.findViewById(R.id.btnHuy);

        edtPrPrice.setText(String.valueOf(product.getGiaban()));
        edtPrName.setText(product.getTensp());
        edtPrQuantity.setText(String.valueOf(product.getSoluong()));


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        btnThem.setOnClickListener(view2 -> {
            int masp = product.getMasp();
            String tensp = edtPrName.getText().toString();
            String giasp = edtPrPrice.getText().toString();
            String slsp = edtPrQuantity.getText().toString();

            if (tensp.isEmpty() || giasp.isEmpty() || slsp.isEmpty()){
                Toast.makeText(context, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
            }else {
                Product editProduct = new Product(masp, tensp, Integer.parseInt(giasp), Integer.parseInt(slsp));
                boolean check = dao.editProduct(editProduct);
                if (check){
                    Toast.makeText(context, "Chinh sua thanh cong", Toast.LENGTH_SHORT).show();
                    lst.clear();
                    lst = dao.getDS();
                    notifyDataSetChanged();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Chinh sua that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuy.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
    }

    private void showDialogDel(String tensp, int masp){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thong bao");
        builder.setMessage("Ban co muon xoa san pham \"" + tensp + "\" khong?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = dao.removeProduct(masp);
                if (check){
                    Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                    lst.clear();
                    lst = dao.getDS();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("cancel", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
