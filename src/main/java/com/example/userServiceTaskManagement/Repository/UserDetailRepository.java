package com.example.userServiceTaskManagement.Repository;

import com.example.userServiceTaskManagement.Entity.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
    UserDetail findByUserMailId(String userName);

    Page<UserDetail> findByUserNameContainingIgnoreCase(String userName, Pageable pageable);
}
