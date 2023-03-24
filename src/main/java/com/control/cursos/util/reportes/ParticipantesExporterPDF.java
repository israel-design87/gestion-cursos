package com.control.cursos.util.reportes;

import com.control.cursos.entidades.Curso;
import com.control.cursos.entidades.Participante;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ParticipantesExporterPDF {

    private List<Participante> listaParticipantes;

    public ParticipantesExporterPDF(List<Participante> listaParticipantes) {
        super();
        this.listaParticipantes = listaParticipantes;
    }

    private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(3);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Nombre del participante", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Email", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Sexo", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Telefono", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Empresa", fuente));
        tabla.addCell(celda);

        celda.setPhrase(new Phrase("Fecha del curso", fuente));
        tabla.addCell(celda);

    }

    private void escribirDatosDeLaTabla(PdfPTable tabla) {
        for (Participante participante : listaParticipantes) {
            tabla.addCell(String.valueOf(participante.getId()));
            tabla.addCell(participante.getNombreParticipante());
            tabla.addCell(participante.getEmail());
            tabla.addCell(participante.getSexo());
            tabla.addCell(String.valueOf(participante.getTelefono()));
            tabla.addCell(participante.getEmpresa());
            tabla.addCell(participante.getFechaCurso().toString());
        }
    }

    public void exportar(HttpServletResponse response) throws DocumentException, IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de participantes", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] { 1f, 6f, 6f, 2.5f, 2.9f, 3.5f, 2f });
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();
    }
}
