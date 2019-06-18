package com.berzenin.university.model.persons;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.berzenin.university.model.university.Group;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
	
	public Student(String name, String surename) {
		this.name=name;
		this.surename=surename;
}

	public Student(long id, String name, String surename) {
		this.id=id;
		this.name=name;
		this.surename=surename;
	}

	public Student(long id, String name, String surename, Group group) {
		this.id=id;
		this.name=name;
		this.surename=surename;
		this.group = group;
	}
	
	public Student(String name, String surename, Group group) {
		this.name=name;
		this.surename=surename;
		this.group = group;
	}

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(name = "group_student", 
			joinColumns = { @JoinColumn(name = "student_id") }, 
			inverseJoinColumns = {@JoinColumn(name = "group_id") })
	private Group group;

}
