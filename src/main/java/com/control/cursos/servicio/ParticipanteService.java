package com.control.cursos.servicio;

import com.control.cursos.entidades.Participante;

import java.util.List;

public interface ParticipanteService {


    List<Participante> findAll(String palabraClave);


    public void save(Participante participante);

    public Participante findOne(Long id);

    public void delete(Long id);
}
