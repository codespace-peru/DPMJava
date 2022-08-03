package com.cmontero.tallerdpmjava.usuarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cmontero.tallerdpmjava.utils.Constantes;
import java.util.ArrayList;
import java.util.Objects;

public class DAOUsuarios {

    DBHelper dbHelper;
    SQLiteDatabase db;

    public DAOUsuarios(Context ctx) {
        dbHelper = new DBHelper(ctx); // DBHelper.getInstance(ctx);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<UsuarioModel> listarUsuarios(){
        ArrayList<UsuarioModel> lista = new ArrayList<>();
        Cursor c = null;
        try{
            c = db.rawQuery("SELECT * FROM " + Constantes.NOMBRETABLA, null);
            UsuarioModel usuario;
            while(c.moveToNext()){
                usuario = new UsuarioModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                lista.add(usuario);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(c).close();
        }
        return lista;
    }

    public long registrarUsuario(UsuarioModel usuario){
        try{
            ContentValues values = new ContentValues();
            values.put("nombres", usuario.getNombres());
            values.put("email", usuario.getEmail());
            values.put("fechaNac", usuario.getFechaNac());
            values.put("sexo", usuario.getSexo());
            return db.insert(Constantes.NOMBRETABLA, null,values);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public UsuarioModel obtenerUsuario(int id){
        UsuarioModel usuario = null;
        String[] args = {String.valueOf(id)};
        Cursor c = null;
        try{
            c = db.rawQuery("SELECT * FROM " + Constantes.NOMBRETABLA + " WHERE id = ?", args);
            while (c.moveToNext()){
                usuario = new UsuarioModel(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Objects.requireNonNull(c).close();
        }
        return usuario;
    }

    public int editarUsuario(UsuarioModel usuario){
        try{
            ContentValues values = new ContentValues();
            values.put("nombres", usuario.getNombres());
            values.put("email", usuario.getEmail());
            values.put("fechaNac", usuario.getFechaNac());
            values.put("sexo", usuario.getSexo());
            String[] args = new String[]{String.valueOf(usuario.getId())};
            return db.update(Constantes.NOMBRETABLA, values, "id=?", args);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int eliminarUsuario(int id){
       try{
           String[] args = {String.valueOf(id)};
           return db.delete(Constantes.NOMBRETABLA, "id=?",args);
       }catch (Exception e){
           e.printStackTrace();
           return -1;
       }
    }

}
