package tw.com.cybersoft.fsd.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DemoRoleFunctionPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 36)
	private String roleId;

	@Column(name = "FUNC_ID", unique = true, nullable = false, length = 36)
	private String funcId;

	public DemoRoleFunctionPK() {
	}

	public DemoRoleFunctionPK(String roleId, String funcId) {
		super();
		this.roleId = roleId;
		this.funcId = funcId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DemoRoleFunctionPK)) {
			return false;
		}
		DemoRoleFunctionPK castOther = (DemoRoleFunctionPK) other;
		return this.roleId.equals(castOther.roleId) && this.funcId.equals(castOther.funcId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleId.hashCode();
		hash = hash * prime + this.funcId.hashCode();

		return hash;
	}

	@Override
	public String toString() {
		return "CcafsRoleFunctionPK [roleId=" + roleId + ", funcId=" + funcId + "]";
	}
}