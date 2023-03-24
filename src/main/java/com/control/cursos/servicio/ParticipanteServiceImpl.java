package com.control.cursos.servicio;

import com.control.cursos.entidades.Participante;
import com.control.cursos.repositorios.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipanteServiceImpl implements ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Participante> findAll(String palabraClave) {

        if(palabraClave != null) {
            return participanteRepository.findAll(palabraClave);
        }
        return participanteRepository.findAll();
    }


    @Override
    @Transactional
    public void save(Participante participante) {
        participanteRepository.save(participante);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        participanteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Participante findOne(Long id) {
        return participanteRepository.findById(id).orElse(null);
    }

}
