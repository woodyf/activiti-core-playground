package tw.com.cybersoft.fsd.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DemoUserRolePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "USER_ID", nullable = false, length = 36)
	private String userId;

	@Column(name = "ROLE_ID", nullable = false, length = 36)
	private String roleId;

	public DemoUserRolePK() {
	}

	public DemoUserRolePK(String userId, String roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DemoUserRolePK)) {
			return false;
		}
		DemoUserRolePK castOther = (DemoUserRolePK) other;
		return this.userId.equals(castOther.userId) && this.roleId.equals(castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.roleId.hashCode();

		return hash;
	}

	@Override
	public String toString() {
		return "CcafsUserRolePK [userId=" + userId + ", roleId=" + roleId + "]";
	}
}