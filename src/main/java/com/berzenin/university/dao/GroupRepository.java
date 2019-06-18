package com.berzenin.university.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.berzenin.university.model.persons.Student;
import com.berzenin.university.model.university.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>  {
	
	Optional<Group> findByName(String name);
	
	List<Group> findByNameContaining(String name);
	
	Optional<Group> findByStudents(Student student);

}
