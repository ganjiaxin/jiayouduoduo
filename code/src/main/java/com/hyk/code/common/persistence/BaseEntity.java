/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.common.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyk.code.common.supcan.annotation.treelist.SupTreeList;
import com.hyk.code.common.supcan.annotation.treelist.cols.SupCol;
import com.hyk.code.common.utils.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Entity支持类
 * @author 霍中曦
 *
 */
@SupTreeList
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected boolean isNewRecord = false;
	/**
	 * 实体编号（唯一标识）
	 */

	protected String id;


	public BaseEntity(String id) {
		this();
		this.id = id;
	}

	@SupCol(isUnique="true", isHide="true")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsNewRecord() {
		return isNewRecord || StringUtils.isBlank(getId());
	}

	/**
	 * 插入之前执行方法，子类实现
	 */
	public abstract void preInsert();

	/**
	 * 更新之前执行方法，子类实现
	 */
	public abstract void preUpdate();

	/**
	 * 当前实体分页对象
	 */
	protected Page<T> page;
	
	public BaseEntity() {
		
	}

	@JsonIgnore
	@XmlTransient
	public Page<T> getPage() {
		if (page == null){
			page = new Page<T>();
		}
		return page;
	}
	
	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	
}
