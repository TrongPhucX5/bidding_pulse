package vn.edu.uth.biddingpulse.repository;

import vn.edu.uth.biddingpulse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select user from User user where user.username = :username")
	User findByUsernameForUpdate(@Param("username") String username);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select user from User user where user.id = :id")
	User findByIdForUpdate(@Param("id") Long id);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
