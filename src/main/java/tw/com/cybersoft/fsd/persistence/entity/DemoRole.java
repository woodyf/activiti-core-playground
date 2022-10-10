package tw.com.cybersoft.fsd.persistence.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "CCAFS_ROLE")
public class DemoRole extends AuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "ROLE_CODE", nullable = false, length = 50)
	private String roleCode;

	@Column(name = "ROLE_NAME", length = 200)
	private String roleName;

	@Column(name = "ROLE_DESC", length = 500)
	private String roleDesc;

	// bi-directional many-to-one association to CcafsRoleFunction
	@OneToMany(mappedBy = "ccafsRole", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<DemoRoleFunction> ccafsRoleFunctions = new LinkedHashSet<>(0);

	// bi-directional many-to-one association to CcafsUserRole
	@OneToMany(mappedBy = "ccafsRole", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<DemoUserRole> ccafsUserRoles = new LinkedHashSet<>(0);

	public DemoRole() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<DemoRoleFunction> getCcafsRoleFunctions() {
		return this.ccafsRoleFunctions;
	}

	public void setCcafsRoleFunctions(Set<DemoRoleFunction> ccafsRoleFunctions) {
		this.ccafsRoleFunctions = ccafsRoleFunctions;
	}

	public DemoRoleFunction addCcafsRoleFunction(DemoRoleFunction ccafsRoleFunction) {
		getCcafsRoleFunctions().add(ccafsRoleFunction);
		ccafsRoleFunction.setCcafsRole(this);

		return ccafsRoleFunction;
	}

	public DemoRoleFunction removeCcafsRoleFunction(DemoRoleFunction ccafsRoleFunction) {
		getCcafsRoleFunctions().remove(ccafsRoleFunction);
		ccafsRoleFunction.setCcafsRole(null);

		return ccafsRoleFunction;
	}

	public Set<DemoUserRole> getCcafsUserRoles() {
		return this.ccafsUserRoles;
	}

	public void setCcafsUserRoles(Set<DemoUserRole> ccafsUserRoles) {
		this.ccafsUserRoles = ccafsUserRoles;
	}

	public DemoUserRole addCcafsUserRole(DemoUserRole ccafsUserRole) {
		getCcafsUserRoles().add(ccafsUserRole);
		ccafsUserRole.setCcafsRole(this);

		return ccafsUserRole;
	}

	public DemoUserRole removeCcafsUserRole(DemoUserRole ccafsUserRole) {
		getCcafsUserRoles().remove(ccafsUserRole);
		ccafsUserRole.setCcafsRole(null);

		return ccafsUserRole;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DemoRole other = (DemoRole) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CcafsRole [id=" + id + ", roleCode=" + roleCode + ", roleName=" + roleName + ", roleDesc=" + roleDesc
				+ ", updateUser=" + updateUser + ", updateUserName=" + updateUserName + ", updateDate=" + updateDate 
				+ ", createUser=" + createUser + ", createUserName=" + createUserName + ", createDate=" + createDate + "]";
	}

}