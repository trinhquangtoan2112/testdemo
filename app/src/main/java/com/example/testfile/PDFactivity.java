package com.example.testfile;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;
import java.nio.file.Path;

public class PDFactivity extends AppCompatActivity {
   String filePath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity);


        PDFView pdfView=findViewById(R.id.pdfview);
        filePath =getIntent().getStringExtra("path");

        File file = new File(filePath);
        Uri path = Uri.fromFile(file);
        pdfView.fromUri(path)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(2)
                .load();


    }
}