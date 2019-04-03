package procurement.entity.risk;
// Generated Apr 14, 2018 1:58:23 PM by Hibernate Tools 5.2.8.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Conflicts generated by hbm2java
 */
@Entity
@Table(name = "conflicts", catalog = "risk_manager_update")
public class Conflicts implements java.io.Serializable {

	private Integer		id;
	private Methods		methodsByMethod2Id;
	private Methods		methodsByMethod1Id;
	private Projects	projects;
	private String		code;
	private Date		createdAt;
	private int			deleted;

	public Conflicts() {
	}

	public Conflicts(Methods methodsByMethod2Id, Methods methodsByMethod1Id, Projects projects, int deleted) {
		this.methodsByMethod2Id = methodsByMethod2Id;
		this.methodsByMethod1Id = methodsByMethod1Id;
		this.projects = projects;
		this.deleted = deleted;
	}

	public Conflicts(Methods methodsByMethod2Id, Methods methodsByMethod1Id, Projects projects, String code,
			Date createdAt, int deleted) {
		this.methodsByMethod2Id = methodsByMethod2Id;
		this.methodsByMethod1Id = methodsByMethod1Id;
		this.projects = projects;
		this.code = code;
		this.createdAt = createdAt;
		this.deleted = deleted;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "method_2_id", nullable = false)
	public Methods getMethodsByMethod2Id() {
		return this.methodsByMethod2Id;
	}

	public void setMethodsByMethod2Id(Methods methodsByMethod2Id) {
		this.methodsByMethod2Id = methodsByMethod2Id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "method_1_id", nullable = false)
	public Methods getMethodsByMethod1Id() {
		return this.methodsByMethod1Id;
	}

	public void setMethodsByMethod1Id(Methods methodsByMethod1Id) {
		this.methodsByMethod1Id = methodsByMethod1Id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	public Projects getProjects() {
		return this.projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	@Column(name = "code", length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "deleted", nullable = false)
	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

}
