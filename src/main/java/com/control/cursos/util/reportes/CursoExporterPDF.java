package com.control.cursos.util.reportes;

import com.control.cursos.entidades.Curso;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CursoExporterPDF {

    private List<Curso> listaCursos;

    public CursoExporterPDF(List<Curso> listaCursos) {
        super();
        this.listaCursos = listaCursos;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre del curso", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Duracion", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha del curso", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Modalidad", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Costo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("I.V.A", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Descuento", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Total", fuente));
        tabla.addCell(celda);
    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Curso curso : listaCursos) {
            tabla.addCell(String.valueOf(curso.getId()));
            tabla.addCell(curso.getNombreCurso());
            tabla.addCell(curso.getDuracion());
            tabla.addCell(curso.getFecha().toString());
            tabla.addCell(curso.getModalidad());
            tabla.addCell(String.valueOf(curso.getCosto()));
            tabla.addCell(String.valueOf(curso.getIva()));
            tabla.addCell(String.valueOf(curso.getDescuento()));
            tabla.addCell(String.valueOf(curso.getTotal()));
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de cursos", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(9);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 1f, 2.3f, 2.3f, 6f, 2.9f, 3.5f, 2f, 2.2f,2.2f });
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}
