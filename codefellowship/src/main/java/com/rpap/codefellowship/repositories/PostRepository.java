package com.rpap.codefellowship.repositories;

import com.rpap.codefellowship.models.Post;
import com.rpap.codefellowship.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//public Post findPostByUsername(String username);

}
