package com.control.cursos.entidades;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombreCurso;

    @NotEmpty
    private String duracion;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @NotNull
    private double participantes;

    @NotEmpty
    private String modalidad;

    @NotNull
    private double costo;

    @NotNull
    private double iva;

    private double rebaja;

    private double descuento;

    @NotNull
    private double total;

    private boolean checkboxRebaja;

    private boolean checkboxDescuento;


    public Curso() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getParticipantes() {
        return participantes;
    }

    public void setParticipantes(double participantes) {
        this.participantes = participantes;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getRebaja() {
        return rebaja;
    }

    public void setRebaja(double rebaja) {
        this.rebaja = rebaja;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isCheckboxDescuento() {
        return checkboxDescuento;
    }

    public void setCheckboxDescuento(boolean checkboxDescuento) {
        this.checkboxDescuento = checkboxDescuento;
    }

    public boolean isCheckboxRebaja() {
        return checkboxRebaja;
    }

    public void setCheckboxRebaja(boolean checkboxRebaja) {
        this.checkboxRebaja = checkboxRebaja;
    }
}
