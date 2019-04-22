/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.sys.service;

import com.hyk.code.common.service.TreeService;
import com.hyk.code.modules.sys.dao.OfficeDao;
import com.hyk.code.modules.sys.entity.Office;
import com.hyk.code.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构Service
 * @author wyw
 *
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			office.setParentIds(office.getParentIds()+"%");
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	public List<Office> queryByGys(Office office){
			return dao.queryByGys(office);
	}
	
	public List<Office> queryByOfficeid(List<String> list){
			return dao.queryByOfficeid(list);
	}
	public List<Office> queryByOfficeidOne(String id){
		return dao.queryByOfficeidOne(id);
	}
	public List<Office> queryByGsjg(Office office){
		return dao.queryByGsjg(office);
	}
	public List<Office> queryByOther(Office office){
		return dao.queryByOther(office);
	}
}
