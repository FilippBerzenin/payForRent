package com.berzenin.university.model.university;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(exclude = "courses")
public class Exercise {
	
	public Exercise() {		
	}

	public Exercise(Long id, @NotNull String name, @NotNull LocalDate date, @NotNull LocalTime timeBegin,
			@NotNull LocalTime timeFinish, Set<Course> courses) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.timeBegin = timeBegin;
		this.timeFinish = timeFinish;
		this.courses = courses;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String name;	
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@NotNull
	@DateTimeFormat(pattern = "HH-mm")
	private LocalTime timeBegin;
	
	@NotNull
	@DateTimeFormat(pattern = "HH-mm")
	private LocalTime timeFinish;
	
	@ManyToMany
	@JoinTable(
		name = "exercise_course", 
		joinColumns = { @JoinColumn(name = "exercise_id") }, 
		inverseJoinColumns = {@JoinColumn(name = "course_id") })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Course> courses;
	
}
