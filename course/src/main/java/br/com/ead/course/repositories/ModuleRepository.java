package br.com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ead.course.models.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {

}
