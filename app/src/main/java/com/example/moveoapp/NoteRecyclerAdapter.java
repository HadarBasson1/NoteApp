package com.example.moveoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moveoapp.Model.Note;

import java.util.List;


class NoteViewHolder extends RecyclerView.ViewHolder {
    //    TextView nameTv;
    TextView titelTv;
    TextView dateTv;
//    CheckBox cb;
    List<Note> data;
//    ImageView avatarImage;

    public NoteViewHolder(@NonNull View itemView, NoteRecyclerAdapter.OnItemClickListener listener, List<Note> data) {
        super(itemView);
        this.data = data;
        titelTv = itemView.findViewById(R.id.note_title);
        dateTv=itemView.findViewById(R.id.note_date);
//        idTv = itemView.findViewById(R.id.studentlistrow_id_tv);
//        avatarImage = itemView.findViewById(R.id.studentlistrow_avatar_img);
//        cb = itemView.findViewById(R.id.studentlistrow_cb);
//        cb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos = (int)cb.getTag();
//                Student st = data.get(pos);
//                st.cb = cb.isChecked();
//            }
//        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Note note, int pos) {
        titelTv.setText(note.getTitle());
        dateTv.setText(note.getDate());
//        idTv.setText(st.id);
//        cb.setChecked(st.cb);
//        cb.setTag(pos);
//        if (st.getAvatarUrl()  != null && st.getAvatarUrl().length() > 5) {
//            Picasso.get().load(st.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImage);
//        }else{
//            avatarImage.setImageResource(R.drawable.avatar);
//        }
//    }
    }
}
public class NoteRecyclerAdapter  extends RecyclerView.Adapter<NoteViewHolder>{
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Note> data;
    public void setData(List<Note> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public NoteRecyclerAdapter(LayoutInflater inflater, List<Note> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_list_row,parent,false);
        return new NoteViewHolder(view,listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = data.get(position);
        holder.bind(note,position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
