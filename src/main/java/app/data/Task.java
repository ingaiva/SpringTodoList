package app.data;

import java.util.Date;

//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Task {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long idTask;
	private String title ;
	private String category;
	private String label;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;
	
	public Task() {
	}

	public Long getIdTask() {
		return idTask;
	}

	public void setIdTask(Long idTask) {
		this.idTask = idTask;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Task [" + (idTask != null ? "idTask=" + idTask + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (category != null ? "category=" + category + ", " : "")
				+ (label != null ? "label=" + label + ", " : "") + (date != null ? "date=" + date + ", " : "")
				+ (user != null ? "user=" + user.getName() : "") + "]";
	}

	
}
