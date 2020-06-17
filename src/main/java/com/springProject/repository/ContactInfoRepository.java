package com.springProject.repository;

import com.springProject.model.ContactInfo;
import com.springProject.model.Holiday;
import com.springProject.model.User;
import com.springProject.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Repository("contactInfoRepository")
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {

    ContactInfo findById(Integer id);

    @Transactional
    @Modifying
    @Query(value="update contact_info set city = :v_city, street = :v_street, street_number = :v_street_number, phone = :v_phone where contact_info_id = :v_id", nativeQuery = true)
    void updateContactInfo(@Param("v_city") String v_city,
                                  @Param("v_street") String v_street,
                                  @Param("v_street_number") Integer v_street_number,
                                  @Param("v_phone") String v_phone,
                                  @Param("v_id") int v_id);
}
