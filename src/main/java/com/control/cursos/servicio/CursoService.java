package com.control.cursos.servicio;

import com.control.cursos.entidades.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CursoService {

    public List<Curso> findAll();

    public Page<Curso> findAll(Pageable pageable);

    public void save(Curso curso);

    public Curso findOne(Long id);

    public void delete(Long id);
}
