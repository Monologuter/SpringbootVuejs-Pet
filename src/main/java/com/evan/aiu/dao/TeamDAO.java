package com.evan.aiu.dao;


import com.evan.aiu.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDAO extends JpaRepository<Team,Integer> {

}
