package com.uniovi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniovi.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	

	
}