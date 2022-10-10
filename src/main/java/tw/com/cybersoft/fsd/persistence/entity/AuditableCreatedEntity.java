package tw.com.cybersoft.fsd.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import tw.com.cybersoft.fsd.common.AuditUserBean;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableCreatedEntity {

	@CreatedBy
	private transient AuditUserBean createdUser;

	@Column(name = "CREATE_USER", length = 10, updatable = false)
	protected String createUser;

	@Column(name = "CREATE_USER_NAME", length = 20, updatable = false)
	protected String createUserName;

	@CreatedDate
	@Column(name = "CREATE_DATE", updatable = false)
	protected LocalDateTime createDate;

	@PrePersist
	protected void prePersist() {
		if (this.createdUser != null) {
			this.createUser = createdUser.getUserId();
			this.createUserName = createdUser.getUserName();
			this.createdUser = null;
		}
	}

	public AuditUserBean getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(AuditUserBean createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
