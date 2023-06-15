package com;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.e.crudoperation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
//{
//
//    @NonNull
//    @Override
//    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_ui,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        CircleImageView circleImageView;
//        TextView name,jobrole,degree;
//
//        public ViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//            circleImageView=itemView.findViewById(R.id.img1);
//            name=itemView.findViewById(R.id.nameText);
//            jobrole=itemView.findViewById(R.id.jobRoleText);
//            degree=itemView.findViewById(R.id.roleText);
//
//        }
//    }
//
//
//}

public class MyAdapter extends FirebaseRecyclerAdapter<Model,MyAdapter.ViewHolder>
{

    public MyAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Model model) {

        holder.name.setText(model.getName());
        holder.jobrole.setText(model.getDegree());
        holder.degree.setText(model.getRole());

        Glide.with(holder.circleImageView.getContext())
                .load(model.getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop()
                .error(R.drawable.ic_launcher_background)
                .into(holder.circleImageView);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.circleImageView.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.popup))
                        .setExpanded(false,930)
                        .create();



                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.edt_name);
                EditText role = view.findViewById(R.id.edt_role);
                EditText degree = view.findViewById(R.id.edt_degree);
                EditText url = view.findViewById(R.id.edt_url);

                Button btnUpdate = view.findViewById(R.id.update);

                name.setText(model.getName());
                role.setText(model.getRole());
                degree.setText(model.getDegree());
                url.setText(model.getUrl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("role",role.getText().toString());
                        map.put("degree",degree.getText().toString());
                        map.put("url",url.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("employees")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(),"Data Not Updated!!",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

                holder.btnDelete.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                    builder.setTitle("Are you Sure?");
                    builder.setMessage("Your Data Is Deleted");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("employees")
                                    .child(Objects.requireNonNull(getRef(position).getKey())).removeValue();

                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(holder.name.getContext(),"",Toast.LENGTH_SHORT);

                        }
                    });
                    builder.show();
                });

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_ui,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
                TextView name,jobrole,degree;
                Button edit,btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.img1);
            name=itemView.findViewById(R.id.nameText);
            jobrole=itemView.findViewById(R.id.jobRoleText);
            degree=itemView.findViewById(R.id.roleText);
            edit=itemView.findViewById(R.id.editButton);
            btnDelete=(Button)itemView.findViewById(R.id.deleteButton);

        }


    }
}