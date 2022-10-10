package tw.com.cybersoft.fsd.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CCAFS_USER_ROLE")
public class DemoUserRole extends AuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DemoUserRolePK id;

	// bi-directional many-to-one association to CcafsUser
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
	@JsonBackReference
	private DemoUser ccafsUser;

	// bi-directional many-to-one association to CcafsRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
	@JsonBackReference
	private DemoRole ccafsRole;

	public DemoUserRole() {
	}

	public DemoUserRole(DemoUserRolePK id) {
		super();
		this.id = id;
	}

	public DemoUserRolePK getId() {
		return id;
	}

	public void setId(DemoUserRolePK id) {
		this.id = id;
	}

	public DemoUser getCcafsUser() {
		return ccafsUser;
	}

	public void setCcafsUser(DemoUser ccafsUser) {
		this.ccafsUser = ccafsUser;
	}

	public DemoRole getCcafsRole() {
		return ccafsRole;
	}

	public void setCcafsRole(DemoRole ccafsRole) {
		this.ccafsRole = ccafsRole;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DemoUserRole other = (DemoUserRole) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CcafsUserRole [id=" + id + ", updateUser=" + updateUser + ", updateUserName=" + updateUserName + ", updateDate="
				+ updateDate + ", createUser=" + createUser + ", createUserName=" + createUserName + ", createDate=" + createDate
				+ "]";
	}

}