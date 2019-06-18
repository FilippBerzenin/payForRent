package com.berzenin.university.model.persons;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.berzenin.university.model.university.Course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
//@EqualsAndHashCode
//@lombok.Value
//@Builder
@Entity
//@EqualsAndHashCode(callSuper = true, exclude = "courses")
public class Teacher extends Person {
	
	public Teacher() {
	}

	public Teacher(long id, String name, String surename, Set<Course> courses) {
		this.id=id;
		this.name=name;
		this.surename=surename;
		this.courses=courses;
	}

	public Teacher(String name, String surename, Set<Course> courses) {
		this.name=name;
		this.surename=surename;
		this.courses=courses;
	}

	@ManyToMany
	@JoinTable(
		name = "teacher_courses", 
		joinColumns = { @JoinColumn(name = "teacher_id") }, 
		inverseJoinColumns = {@JoinColumn(name = "courses_id") })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Course> courses;
}
