package tw.com.cybersoft.fsd.persistence.entity;

import java.io.Serializable;
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

import tw.com.cybersoft.fsd.common.FunctionType;

@Entity
@Table(name = "CCAFS_FUNCTION")
public class DemoFunction extends AuditableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "FUNC_TYPE", nullable = false, length = 36)
	private FunctionType funcType;

	@Column(name = "FUNC_CODE", nullable = false, length = 36)
	private String funcCode;

	@Column(name = "FUNC_NAME", length = 200)
	private String funcName;

	@Column(name = "FUNC_DESC", length = 500)
	private String funcDesc;

	@Column(name = "FUNC_ORDER")
	private short funcOrder;

	@Column(name = "PARENT_ID", length = 36)
	private String parentId;

	@Column(name = "IS_ACTIVE", nullable = false, length = 1)
	@Type(type = "yes_no")
	private Boolean isActive;

	// bi-directional many-to-one association to CcafsRoleFunction
	@OneToMany(mappedBy = "ccafsFunction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<DemoRoleFunction> ccafsRoleFunctions = new LinkedHashSet<>(0);

	public DemoFunction() {
	}

	public DemoRoleFunction addCcafsRoleFunction(DemoRoleFunction ccafsRoleFunction) {
		getCcafsRoleFunctions().add(ccafsRoleFunction);
		ccafsRoleFunction.setCcafsFunction(this);
		return ccafsRoleFunction;
	}

	public DemoRoleFunction removeCcafsRoleFunction(DemoRoleFunction ccafsRoleFunction) {
		getCcafsRoleFunctions().remove(ccafsRoleFunction);
		ccafsRoleFunction.setCcafsFunction(null);
		return ccafsRoleFunction;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FunctionType getFuncType() {
		return funcType;
	}

	public void setFuncType(FunctionType funcType) {
		this.funcType = funcType;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncDesc() {
		return funcDesc;
	}

	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}

	public short getFuncOrder() {
		return funcOrder;
	}

	public void setFuncOrder(short funcOrder) {
		this.funcOrder = funcOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<DemoRoleFunction> getCcafsRoleFunctions() {
		return ccafsRoleFunctions;
	}

	public void setCcafsRoleFunctions(Set<DemoRoleFunction> ccafsRoleFunctions) {
		this.ccafsRoleFunctions = ccafsRoleFunctions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DemoFunction other = (DemoFunction) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CcafsFunction [id=" + id + ", funcType=" + funcType + ", funcCode=" + funcCode + ", funcName=" + funcName
				+ ", funcDesc=" + funcDesc + ", funcOrder=" + funcOrder + ", parentId=" + parentId + ", isActive=" + isActive
				+ ", updateUser=" + updateUser + ", updateUserName=" + updateUserName + ", updateDate=" + updateDate
				+ ", createUser=" + createUser + ", createUserName=" + createUserName + ", createDate=" + createDate + "]";
	}

}