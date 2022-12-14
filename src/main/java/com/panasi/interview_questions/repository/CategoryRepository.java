package com.panasi.interview_questions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.panasi.interview_questions.repository.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	public Category findByName(String name);
	public List<Category> findAllByParentId(int parentId);
}
