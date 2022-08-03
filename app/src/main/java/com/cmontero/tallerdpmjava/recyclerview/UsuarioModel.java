package com.cmontero.tallerdpmjava.recyclerview;

import java.io.Serializable;

public class UsuarioModel implements Serializable {

    private int id;
    private String nombres;
    private String email;
    private String sexo;
    private String fechaNac;

    public UsuarioModel(int id, String nombres, String email, String sexo, String fechaNac) {
        this.id = id;
        this.nombres = nombres;
        this.email = email;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
    }

    public UsuarioModel(String nombres, String email, String sexo, String fechaNac) {
        this.nombres = nombres;
        this.email = email;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }
}
