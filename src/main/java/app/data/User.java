package app.data;

//import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	private String username;	
	private String password;
	
	private String  name;
	private String  email;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(nullable=true)
	private Date dateOfBirth;
	
	@OneToMany (mappedBy = "user")
	List<Task> lstTask = new ArrayList<Task>();

	public User() {		
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Task> getLstTask() {
		return lstTask;
	}

	public void setLstTask(List<Task> lstTask) {
		this.lstTask = lstTask;
	}

	@Override
	public String toString() {
		return "User [" + (idUser != null ? "idUser=" + idUser + ", " : "")
				+ (username != null ? "username=" + username + ", " : "")
				+ (password != null ? "password=" + password + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + (lstTask != null ? "lstTask=" + lstTask.size() : "") + "]";
	}
	
	
	
}
