package com.control.cursos.repositorios;

import com.control.cursos.entidades.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    @Query("SELECT p FROM Participante p WHERE p.idCurso LIKE %?1%")
    public List<Participante> findAll(String palabraClave);
}
