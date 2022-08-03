package com.cmontero.tallerdpmjava.usuarios;

import android.os.Parcel;
import android.os.Parcelable;

public class UsuarioModel implements Parcelable {
    private int id;
    private String nombres;
    private String email;
    private String fechaNac;
    private String sexo;

    public UsuarioModel(int id, String nombres, String email, String fechaNac, String sexo) {
        this.id = id;
        this.nombres = nombres;
        this.email = email;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
    }

    public UsuarioModel(String nombres, String email, String fechaNac, String sexo) {
        this.nombres = nombres;
        this.email = email;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
    }

    protected UsuarioModel(Parcel parcel) {
        id = parcel.readInt();
        nombres = parcel.readString();
        email = parcel.readString();
        fechaNac = parcel.readString();
        sexo = parcel.readString();
    }

    public static final Creator<UsuarioModel> CREATOR = new Creator<UsuarioModel>() {
        @Override
        public UsuarioModel createFromParcel(Parcel in) {
            return new UsuarioModel(in);
        }

        @Override
        public UsuarioModel[] newArray(int size) {
            return new UsuarioModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getEmail() {
        return email;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.nombres);
        parcel.writeString(this.email);
        parcel.writeString(this.fechaNac);
        parcel.writeString(this.sexo);
    }
}
