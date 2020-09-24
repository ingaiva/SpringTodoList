package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.data.User;


@CrossOrigin("*")
@RepositoryRestResource
public interface UserRepositorie extends JpaRepository<User, Long> {
	
	@Query("select u from User u where u.email =:x")
	public List<User> getUserByMail(@Param("x") String email);
}
