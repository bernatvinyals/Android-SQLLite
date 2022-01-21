package com.example.android_sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnALta,btnBaja,btnModify,btnByDesc,btnByCode;
    EditText etCode,etDesc,etPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnALta = findViewById(R.id.btnAlta);
        btnBaja = findViewById(R.id.btnBaja);
        btnModify = findViewById(R.id.btnModify);
        btnByDesc = findViewById(R.id.btnConsultDesc);
        btnByCode = findViewById(R.id.btnConsultCode);
        etCode = findViewById(R.id.etCode);
        etDesc = findViewById(R.id.etDesc);
        etPrice = findViewById(R.id.etPrico);

        btnALta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alta();
            }
        });
        btnBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Baja();
            }
        });
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modify();
            }
        });
        btnByDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultPerDesc();
            }
        });
        btnByCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultPerCode();
            }
        });
    }

    public void Alta() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = etCode.getText().toString();
        String descripcion= etDesc.getText().toString();
        String precio = etPrice.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        bd.insert("articulos", null, registro);
        bd.close();
        etCode.setText("");
        etDesc.setText("");
        etPrice.setText("");
        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public void ConsultPerCode() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = etCode.getText().toString();
        Cursor fila = bd.rawQuery("select descripcion,precio from articulos where codigo=" + codigo, null);
        if (fila.moveToFirst()) {
            etDesc.setText(fila.getString(0));
            etPrice.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No article with that code",Toast.LENGTH_SHORT).show();
        bd.close();
    }
    public void ConsultPerDesc() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String descripcion = etDesc.getText().toString();
        Cursor fila = bd.rawQuery("select codigo,precio from articulos where descripcion='" + descripcion+"'", null);
        if (fila.moveToFirst()) {
            etCode.setText(fila.getString(0));
            etPrice.setText(fila.getString(1));
        } else
        Toast.makeText(this, "No article with that desc",Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void Baja() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo= etCode.getText().toString();
        int cant = bd.delete("articulos", "codigo=" + codigo, null);
        bd.close();
        etCode.setText("");
        etDesc.setText("");
        etPrice.setText("");
        if (cant == 1)Toast.makeText(this, "Deleted Data from code",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No data from Code",Toast.LENGTH_SHORT).show();
    }
    public void Modify() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = etCode.getText().toString();
        String descripcion = etDesc.getText().toString();
        String precio= etPrice.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);
        int cant = bd.update("articulos", registro, "codigo=" + codigo, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "no existe un artículo con el código ingresado",Toast.LENGTH_SHORT).show();
    }
}