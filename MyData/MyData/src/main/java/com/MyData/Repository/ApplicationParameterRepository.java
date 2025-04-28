package com.MyData.Repository;

import com.MyData.Dao.ApplicationParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationParameterRepository extends JpaRepository<ApplicationParameter, Long> {

    @Query("SELECT a.value FROM ApplicationParameter a WHERE a.parameter = :parameter")
    String findValueByParameter(@Param("parameter") String parameter);
}