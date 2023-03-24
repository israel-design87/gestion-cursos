package com.control.cursos.repositorios;

import com.control.cursos.entidades.Curso;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{

}
