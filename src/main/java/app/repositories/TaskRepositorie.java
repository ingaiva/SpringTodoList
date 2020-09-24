package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.data.Task;

@CrossOrigin("*")
@RepositoryRestResource
public interface TaskRepositorie extends JpaRepository<Task, Long> {
	@Query("select t from Task t where t.user.idUser =:x")
	public List<Task> getTasksByUser(@Param("x") Long idUser);
	
	@Query("select t from Task t where t.user.idUser =:x and t.label like:y")
	public List<Task> getTasksByKeyWord(@Param("x") Long idUser, @Param("y") String mc);
}
