package br.com.ead.course.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, UUID> {

}
