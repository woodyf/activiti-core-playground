package tw.com.cybersoft.fsd.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import tw.com.cybersoft.fsd.common.AuditUserBean;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends AuditableCreatedEntity {

	@LastModifiedBy
	private transient AuditUserBean updatedUser;

	@Column(name = "UPDATE_USER", length = 10)
	protected String updateUser;

	@Column(name = "UPDATE_USER_NAME", length = 20)
	protected String updateUserName;

	@LastModifiedDate
	@Column(name = "UPDATE_DATE")
	protected LocalDateTime updateDate;

	@Override
	@PrePersist
	protected void prePersist() {
		super.prePersist();
		setUpdated();
	}

	@PreUpdate
	private void preUpdate() {
		setUpdated();
	}

	private void setUpdated() {
		if (this.updatedUser != null) {
			this.updateUser = updatedUser.getUserId();
			this.updateUserName = updatedUser.getUserName();
			this.updatedUser = null;
		}
	}

	public AuditUserBean getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(AuditUserBean updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

}
