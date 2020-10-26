package com.wander.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wander.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

	@Query("FROM UserAccount WHERE userAccountId= ?1 AND status = 'A' ")
	public Optional<UserAccount> getActiveUser(Long userID);

	@Query("FROM UserAccount WHERE userAccountId= ?1 AND status = 'O' ")
	public Optional<UserAccount> getObsoleteUser(Long userID);

}
