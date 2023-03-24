package com.control.cursos.controlador;

import com.control.cursos.entidades.Curso;
import com.control.cursos.servicio.CursoService;
import com.control.cursos.util.paginacion.PageRender;
import com.control.cursos.util.reportes.CursoExporterExcel;
import com.control.cursos.util.reportes.CursoExporterPDF;
import com.lowagie.text.DocumentException;
import net.bytebuddy.pool.TypePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/verCurso/{id}")
    public String verDetallesDelCurso(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
        Curso curso= cursoService.findOne(id);
        if(curso == null) {
            flash.addFlashAttribute("error", "El curso no existe en la base de datos");
            return "redirect:/listarCurso";
        }

        modelo.put("curso",curso);
        modelo.put("titulo", "Detalles del curso " + curso.getNombreCurso());
        return "verCurso";
    }

    @GetMapping({"/","/listarCurso",""})
    public String listarCursos(@RequestParam(name = "page",defaultValue = "0") int page,Model modelo) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Curso> cursos = cursoService.findAll(pageRequest);
        PageRender<Curso> pageRender = new PageRender<>("/listar", cursos);

        modelo.addAttribute("titulo","Listado de cursos");
        modelo.addAttribute("cursos",cursos);
        modelo.addAttribute("porcentaje","%");
        modelo.addAttribute("page", pageRender);

        return "listarCurso";
    }

    @GetMapping("/formCurso")
    public String mostrarFormularioDeRegistrarCurso(Map<String,Object> modelo) {
        Curso curso = new Curso();
        modelo.put("curso", curso);
        modelo.put("titulo", "Registro de cursos");
        return "formCurso";
    }

    @PostMapping("/formCurso")
    public String guardarCurso(@Valid Curso curso,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
        if(result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de curso");
            return "formCurso";
        }

        String mensaje = (curso.getId() != null) ? "El curso ha sido editato con exito" : "Curso registrado con exito";

        if (curso.isCheckboxDescuento()) {

            double descuento = curso.getRebaja() * curso.getCosto() / 100;
            curso.setDescuento(descuento);
        } else if (curso.isCheckboxRebaja()) {

            double rebaja = curso.getDescuento() / curso.getCosto() * 100;
            curso.setRebaja(rebaja);

        }
        curso.setIva(16.0);
        double iva = curso.getCosto()*curso.getParticipantes() * curso.getIva() / 100;
        double total = curso.getCosto() - curso.getDescuento() + iva;
        curso.setIva(16.0);
        curso.setTotal(total);
        cursoService.save(curso);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarCurso";
    }

    @GetMapping("/formCurso/{id}")
    public String editarCurso(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
        Curso curso = null;
        if(id > 0) {
            curso = cursoService.findOne(id);
            if(curso == null) {
                flash.addFlashAttribute("error", "El ID del curso no existe en la base de datos");
                return "redirect:/listarCurso";
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID del curso no puede ser cero");
            return "redirect:/listarCurso";
        }

        modelo.put("curso",curso);
        modelo.put("titulo", "Edición de curso");
        return "formCurso";
    }

    @GetMapping("/eliminarCurso/{id}")
    public String eliminarCurso(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
        if(id > 0) {
            cursoService.delete(id);
            flash.addFlashAttribute("success", "Curso eliminado con exito");
        }
        return "redirect:/listarCurso";
    }

    @GetMapping("/exportarPDFCurso")
    public void exportarListadoDeCursosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Cursos_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Curso> cursos = cursoService.findAll();

        CursoExporterPDF exporter = new CursoExporterPDF(cursos);
        exporter.exportar(response);
    }

    @GetMapping("/exportarExcelCurso")
    public void exportarListadoDeCursosEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Cursos_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Curso> cursos = cursoService.findAll();

        CursoExporterExcel exporter = new CursoExporterExcel(cursos);
        exporter.exportar(response);
    }
}
