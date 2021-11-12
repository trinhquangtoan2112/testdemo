package com.example.testfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Filter;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements OnPDFSelectList {

    private  MainAdpter mainAdpter;

    private List<File> pdfList;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Filter filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runtimePermission();
    }



    private void runtimePermission(){
        Dexter.withContext(MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayPdf();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> findpdf (File file){
        ArrayList<File> arrayList =new ArrayList<>();
        File[]  files =  file.listFiles();

        for(File signlerfile: files){
            if(signlerfile.isDirectory()&& !signlerfile.isHidden()){
                arrayList.addAll(findpdf(signlerfile));
            }

            else
            {
                if(signlerfile.getName().endsWith(".pdf")){
                    arrayList.add(signlerfile);
                }
            }
        }
        return  arrayList;
    }



    public void displayPdf(){
        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        pdfList=new ArrayList<>();
        pdfList.addAll(findpdf(Environment.getExternalStorageDirectory()));
        mainAdpter =new MainAdpter(this,pdfList,this);
        recyclerView.setAdapter(mainAdpter);
    }

    @Override
    public void onPDFSelected(File file) {
        startActivity(new Intent(MainActivity.this,PDFactivity.class)
                .putExtra("path",file.getAbsolutePath()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);

        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
         searchView = (SearchView) menu.findItem(R.id.search_icon).getActionView();
         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
         searchView.setMaxWidth(Integer.MAX_VALUE);

        return true;
    }







}