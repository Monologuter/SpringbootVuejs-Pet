package com.evan.aiu.dao;


import com.evan.aiu.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 */
public interface CommentsDAO extends JpaRepository<Comments,Integer> {
    List<Comments> findByAdoptname(String adoptname);
    List<Comments> findByAid(Integer aid);
}

