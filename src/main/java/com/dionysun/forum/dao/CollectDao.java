package com.dionysun.forum.dao;

import com.dionysun.forum.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectDao extends JpaRepository<Collect, Long> {

}
