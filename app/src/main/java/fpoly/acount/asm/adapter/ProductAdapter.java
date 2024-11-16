package fpoly.acount.asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
            int id = lst.get(holder.getAdapterPosition()).getMasp();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Thong bao");
            builder.setMessage("Ban co muon xoa san pham " + lst.get(holder.getAdapterPosition()).getTensp());
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean check = dao.removeProduct(id);
                    if (check){
                        Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                        lst.clear();
                        lst = dao.getDS();
                        notifyItemRemoved(holder.getAdapterPosition());
                    }else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.txtEdit.setOnClickListener(view -> {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            View view1 = inflater.inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view1);
            EditText edtPrName, edtPrPrice, edtPrQuantity;
            Button btnThem;
            edtPrName = view1.findViewById(R.id.edtPrName);
            edtPrPrice = view1.findViewById(R.id.PrPrice);
            edtPrQuantity = view1.findViewById(R.id.PrQuantity);
            btnThem = view1.findViewById(R.id.btnThem);

            edtPrPrice.setText(String.valueOf(lst.get(position).getGiaban()));
            edtPrName.setText(lst.get(position).getTensp());
            edtPrQuantity.setText(String.valueOf(lst.get(position).getSoluong()));
            btnThem.setOnClickListener(view2 -> {
                Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
}
