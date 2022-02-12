package com.file.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.file.manager.api.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	   public Lesson findByResource(String resource);
}
