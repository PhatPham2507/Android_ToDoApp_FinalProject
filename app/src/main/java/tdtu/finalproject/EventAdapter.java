package tdtu.finalproject;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;



import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import com.orhanobut.dialogplus.ViewHolder;



import java.util.ArrayList;
import java.util.HashMap;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends FirebaseRecyclerAdapter<Event, EventAdapter.EventViewHolder> {

    ArrayList<Event> eventArrayList;
    Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirebaseRecyclerOptions<Event> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event model) {
        holder.addtitle.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.addtitle.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_update))
                        .setExpanded(true,1200).create();


                View viewUpdate = dialogPlus.getHolderView();

                EditText title = viewUpdate.findViewById(R.id.TitleUpdate);
                EditText description = viewUpdate.findViewById(R.id.DescriptionUpdate);
                EditText date = viewUpdate.findViewById(R.id.DateUpdate);
                EditText time = viewUpdate.findViewById(R.id.TimeUpdate);
                EditText event = viewUpdate.findViewById(R.id.UpdateEvent);

                Button updateBtn = viewUpdate.findViewById(R.id.UpdateTask);

                title.setText(model.getTitle());
                description.setText(model.getDescription());
                date.setText(model.getDate());
                time.setText(model.getTime());
                event.setText(model.getAnEvent());

                dialogPlus.show();

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap map = new HashMap<>();
                        map.put("title", title.getText().toString());
                        map.put("description", description.getText().toString());
                        map.put("date", date.getText().toString());
                        map.put("time", time.getText().toString());
                        map.put("anEvent", event.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Event")
                                .child(model.getId()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.addtitle.getContext(), "Data Updated Successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.addtitle.getContext(), "Data Updated Error",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.addtitle.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Delete data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("Event").child(model.getId()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.addtitle.getContext(),"Delete Successfully",Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.addtitle.getContext(),"Delete Failed",Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.addtitle.getContext(), "Cancelled",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);
        return new EventViewHolder(view);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView addtitle, description, date, time, anEvent;
        ImageView options;
        Button updateBtn, editBtn, deleteBtn;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            addtitle =  itemView.findViewById(R.id.title);
            description =  itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            time =  itemView.findViewById(R.id.time);


            editBtn =  itemView.findViewById(R.id.editTask);
            deleteBtn =  itemView.findViewById(R.id.delete);
            updateBtn =  itemView.findViewById(R.id.UpdateEvent);




        }


    }

}