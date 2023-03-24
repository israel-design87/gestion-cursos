package com.control.cursos.controlador;

import com.control.cursos.entidades.Participante;
import com.control.cursos.servicio.ParticipanteService;
import com.control.cursos.util.reportes.ParticipantesExporterExcel;
import com.control.cursos.util.reportes.ParticipantesExporterPDF;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping("/verParticipantes/{id}")
    public String verDetallesDelParticipante(@PathVariable(value = "id") Long id,Map<String,Object> modelo,RedirectAttributes flash) {
        Participante participante= participanteService.findOne(id);
        if(participante == null) {
            flash.addFlashAttribute("error", "El participante no existe en la base de datos");
            return "redirect:/listarParticipantes";
        }

        modelo.put("participante",participante);
        modelo.put("titulo", "Detalles del participante " + participante.getNombreParticipante());
        return "verParticipantes";
    }

    @GetMapping("/listarParticipantes")
    public String listarParticipante(Model modelo,@Param("palabraClave")String palabraClave) {
        List<Participante> participante = participanteService.findAll(palabraClave);

        modelo.addAttribute("titulo","Listado de participantes");
        modelo.addAttribute("participante",participante);

        return "listarParticipantes";
    }

    @GetMapping("/formParticipantes")
    public String mostrarFormularioDeRegistrarParticipantes(Map<String,Object> modelo) {
        Participante participante = new Participante();
        modelo.put("participante", participante);
        modelo.put("titulo", "Registro de participantes");
        return "formParticipantes";
    }

    @PostMapping("/formParticipantes")
    public String guardarParticipante(@Valid Participante participante,BindingResult result,Model modelo,RedirectAttributes flash,SessionStatus status) {
        if(result.hasErrors()) {
            modelo.addAttribute("titulo", "Registro de participante");
            return "formParticipantes";
        }

        String mensaje = (participante.getId() != null) ? "El participante ha sido editato con exito" : "Participante registrado con exito";

        participanteService.save(participante);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/listarParticipantes";
    }

    @GetMapping("/formParticipantes/{id}")
    public String editarParticipante(@PathVariable(value = "id") Long id,Map<String, Object> modelo,RedirectAttributes flash) {
        Participante participante = null;
        if(id > 0) {
            participante = participanteService.findOne(id);
            if(participante == null) {
                flash.addFlashAttribute("error", "El ID del participante no existe en la base de datos");
                return "redirect:/listarParticipantes";
            }
        }
        else {
            flash.addFlashAttribute("error", "El ID del participante no puede ser cero");
            return "redirect:/listarParticipantes";
        }

        modelo.put("participante",participante);
        modelo.put("titulo", "Edición de participante");
        return "formParticipantes";
    }

    @GetMapping("/eliminarParticipantes/{id}")
    public String eliminarParticipante(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
        if(id > 0) {
            participanteService.delete(id);
            flash.addFlashAttribute("success", "Participante eliminado con exito");
        }
        return "redirect:/listarParticipantes";
    }

    @GetMapping("/exportarPDFParticipantes")
    public void exportarListadoDeParticipantesEnPDF(@Param("palabraClave1")String palabraClave1,HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Participantes_" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Participante> participantes = participanteService.findAll(palabraClave1);

        ParticipantesExporterPDF exporter = new ParticipantesExporterPDF(participantes);
        exporter.exportar(response);
    }

    @GetMapping("/exportarExcelParticipantes")
    public void exportarListadoDeParticipantesEnExcel(@Param("palabraClave2")String palabraClave2,HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Participantes_" + fechaActual + ".xlsx";

        response.setHeader(cabecera, valor);

        List<Participante> participantes = participanteService.findAll(palabraClave2);

        ParticipantesExporterExcel exporter = new ParticipantesExporterExcel(participantes);
        exporter.exportar(response);
    }
}
