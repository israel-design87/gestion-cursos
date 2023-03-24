package com.control.cursos.util.reportes;

import com.control.cursos.entidades.Curso;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CursoExporterExcel {

    private XSSFWorkbook libro;
    private XSSFSheet hoja;

    private List<Curso> listaCursos;

    public CursoExporterExcel(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
        libro = new XSSFWorkbook();
        hoja = libro.createSheet("Cursos");
    }

    private void escribirCabeceraDeTabla() {
        Row fila = hoja.createRow(0);

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setBold(true);
        fuente.setFontHeight(16);
        estilo.setFont(fuente);

        Cell celda = fila.createCell(0);
        celda.setCellValue("ID");
        celda.setCellStyle(estilo);

        celda = fila.createCell(1);
        celda.setCellValue("Nombre del Curso");
        celda.setCellStyle(estilo);

        celda = fila.createCell(2);
        celda.setCellValue("Duracion");
        celda.setCellStyle(estilo);

        celda = fila.createCell(3);
        celda.setCellValue("Fecha del curso");
        celda.setCellStyle(estilo);

        celda = fila.createCell(4);
        celda.setCellValue("Modalidad");
        celda.setCellStyle(estilo);

        celda = fila.createCell(5);
        celda.setCellValue("Costo");
        celda.setCellStyle(estilo);

        celda = fila.createCell(6);
        celda.setCellValue("I.V.A");
        celda.setCellStyle(estilo);

        celda = fila.createCell(7);
        celda.setCellValue("Descuento");
        celda.setCellStyle(estilo);

        celda = fila.createCell(8);
        celda.setCellValue("Total");
        celda.setCellStyle(estilo);
    }

    private void escribirDatosDeLaTabla() {
        int nueroFilas = 1;

        CellStyle estilo = libro.createCellStyle();
        XSSFFont fuente = libro.createFont();
        fuente.setFontHeight(14);
        estilo.setFont(fuente);

        for (Curso curso : listaCursos) {
            Row fila = hoja.createRow(nueroFilas++);

            Cell celda = fila.createCell(0);
            celda.setCellValue(curso.getId());
            hoja.autoSizeColumn(0);
            celda.setCellStyle(estilo);

            celda = fila.createCell(1);
            celda.setCellValue(curso.getNombreCurso());
            hoja.autoSizeColumn(1);
            celda.setCellStyle(estilo);

            celda = fila.createCell(2);
            celda.setCellValue(curso.getDuracion());
            hoja.autoSizeColumn(2);
            celda.setCellStyle(estilo);

            celda = fila.createCell(3);
            celda.setCellValue(curso.getFecha().toString());
            hoja.autoSizeColumn(3);
            celda.setCellStyle(estilo);

            celda = fila.createCell(4);
            celda.setCellValue(curso.getModalidad());
            hoja.autoSizeColumn(4);
            celda.setCellStyle(estilo);

            celda = fila.createCell(5);
            celda.setCellValue(curso.getCosto());
            hoja.autoSizeColumn(5);
            celda.setCellStyle(estilo);

            celda = fila.createCell(6);
            celda.setCellValue(curso.getIva());
            hoja.autoSizeColumn(6);
            celda.setCellStyle(estilo);

            celda = fila.createCell(7);
            celda.setCellValue(curso.getDescuento());
            hoja.autoSizeColumn(7);
            celda.setCellStyle(estilo);

            celda = fila.createCell(8);
            celda.setCellValue(curso.getTotal());
            hoja.autoSizeColumn(8);
            celda.setCellStyle(estilo);
        }
    }

    public void exportar(HttpServletResponse response) throws IOException {
        escribirCabeceraDeTabla();
        escribirDatosDeLaTabla();

        ServletOutputStream outPutStream = response.getOutputStream();
        libro.write(outPutStream);

        libro.close();
        outPutStream.close();
    }
}
