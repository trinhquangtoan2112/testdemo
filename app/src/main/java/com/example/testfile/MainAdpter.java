package com.example.testfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.CharArrayWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainAdpter extends RecyclerView.Adapter<mainholder>  {

    private Context context;
    private static List<File> pdfFile;
    private OnPDFSelectList listener;
    private static List<File> pdfFileOld;

    public MainAdpter(Context context, List<File> pdfFile, OnPDFSelectList listener) {
        this.context = context;
        this.pdfFile = pdfFile;
        this.listener = listener;
        this.pdfFileOld =pdfFileOld;
    }

    @NonNull
    @Override
    public mainholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mainholder(LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull mainholder holder, int position) {
        holder.txt.setText(pdfFile.get(position).getName());
        holder.txt.setSelected(true);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 listener.onPDFSelected(pdfFile.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfFile.size();
    }


}
