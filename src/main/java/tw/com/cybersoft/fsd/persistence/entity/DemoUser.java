package tw.com.cybersoft.fsd.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import tw.com.cybersoft.fsd.common.MgmtType;
import tw.com.cybersoft.fsd.common.ReviewStatus;

@Entity
@Table(name = "CCAFS_USER")
public class DemoUser extends AuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "EMP_NO", nullable = false, length = 10)
	private String empNo;

	@Column(name = "EMP_NAME", length = 100)
	private String empName;

	@Column(name = "EXT", length = 20)
	private String ext;

	@Column(name = "IS_ENABLE", nullable = false, length = 1)
	@Type(type = "yes_no")
	private Boolean isEnable;

	@Column(name = "IS_ACTIVE", nullable = false, length = 1)
	@Type(type = "yes_no")
	private Boolean isActive;

	@Column(name = "BACK_DESC", length = 500)
	private String backDesc;

	@Enumerated(EnumType.STRING)
	@Column(name = "MGMT_TYPE", nullable = false, length = 10)
	private MgmtType mgmtType;

	@Enumerated(EnumType.STRING)
	@Column(name = "REVIEW_STATUS", nullable = false, length = 10)
	private ReviewStatus reviewStatus;

	@Column(name = "REVIEW_USER", length = 10)
	private String reviewUser;

	@Column(name = "REVIEW_USER_NAME", length = 20)
	private String reviewUserName;

	@Column(name = "REVIEW_DATE")
	private LocalDateTime reviewDate;

	// bi-directional many-to-one association to CcafsUserRole
	@OneToMany(mappedBy = "ccafsUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<DemoUserRole> ccafsUserRoles = new LinkedHashSet<>(0);

	public DemoUser() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getBackDesc() {
		return backDesc;
	}

	public void setBackDesc(String backDesc) {
		this.backDesc = backDesc;
	}

	public MgmtType getMgmtType() {
		return mgmtType;
	}

	public void setMgmtType(MgmtType mgmtType) {
		this.mgmtType = mgmtType;
	}

	public ReviewStatus getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(ReviewStatus reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	public String getReviewUserName() {
		return reviewUserName;
	}

	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

	public LocalDateTime getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(LocalDateTime reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Set<DemoUserRole> getCcafsUserRoles() {
		return ccafsUserRoles;
	}

	public void setCcafsUserRoles(Set<DemoUserRole> ccafsUserRoles) {
		this.ccafsUserRoles = ccafsUserRoles;
	}

	public DemoUserRole addCcafsUserRole(DemoUserRole ccafsUserRole) {
		getCcafsUserRoles().add(ccafsUserRole);
		ccafsUserRole.setCcafsUser(this);

		return ccafsUserRole;
	}

	public DemoUserRole removeCcafsUserRole(DemoUserRole ccafsUserRole) {
		getCcafsUserRoles().remove(ccafsUserRole);
		ccafsUserRole.setCcafsUser(null);

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
		DemoUser other = (DemoUser) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CcafsUser [id=" + id + ", empNo=" + empNo + ", empName=" + empName + ", ext=" + ext + ", isEnable=" + isEnable
				+ ", isActive=" + isActive + ", backDesc=" + backDesc + ", mgmtType=" + mgmtType + ", reviewStatus="
				+ reviewStatus + ", reviewUser=" + reviewUser + ", reviewUserName=" + reviewUserName + ", reviewDate="
				+ reviewDate + ", updateUser=" + updateUser + ", updateUserName=" + updateUserName + ", updateDate=" + updateDate
				+ ", createUser=" + createUser + ", createUserName=" + createUserName + ", createDate=" + createDate + "]";
	}

}