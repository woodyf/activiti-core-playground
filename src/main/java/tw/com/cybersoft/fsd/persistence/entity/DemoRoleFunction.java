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
@Table(name = "CCAFS_ROLE_FUNCTION")
public class DemoRoleFunction extends AuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DemoRoleFunctionPK id;

	// bi-directional many-to-one association to CcafsRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
	@JsonBackReference
	private DemoRole ccafsRole;

	// bi-directional many-to-one association to CcafsFunction
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNC_ID", nullable = false, insertable = false, updatable = false)
	@JsonBackReference
	private DemoFunction ccafsFunction;

	public DemoRoleFunction() {
	}

	public DemoRoleFunction(DemoRoleFunctionPK id) {
		super();
		this.id = id;
	}

	public DemoRoleFunctionPK getId() {
		return id;
	}

	public void setId(DemoRoleFunctionPK id) {
		this.id = id;
	}

	public DemoRole getCcafsRole() {
		return ccafsRole;
	}

	public void setCcafsRole(DemoRole ccafsRole) {
		this.ccafsRole = ccafsRole;
	}

	public DemoFunction getCcafsFunction() {
		return ccafsFunction;
	}

	public void setCcafsFunction(DemoFunction ccafsFunction) {
		this.ccafsFunction = ccafsFunction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DemoRoleFunction other = (DemoRoleFunction) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CcafsRoleFunction [id=" + id + ", updateUser=" + updateUser + ", updateUserName=" + updateUserName
				+ ", updateDate=" + updateDate + ", createUser=" + createUser + ", createUserName=" + createUserName
				+ ", createDate=" + createDate + "]";
	}

}
