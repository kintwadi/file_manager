package com.file.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.file.manager.api.model.Topic;

@Repository
public interface TopicRepository  extends JpaRepository<Topic,Long>{

}
