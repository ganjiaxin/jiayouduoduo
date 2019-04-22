/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.common;

import com.hyk.code.common.utils.CacheUtils;
import com.hyk.code.common.utils.SpringContextHolder;
import com.hyk.code.modules.sys.dao.MenuDao;
import com.hyk.code.modules.sys.dao.RoleDao;
import com.hyk.code.modules.sys.dao.UserDao;
import com.hyk.code.modules.sys.entity.Menu;
import com.hyk.code.modules.sys.entity.Role;
import com.hyk.code.modules.sys.entity.User;
import com.hyk.code.modules.sys.security.SystemAuthorizingRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户工具类
 * @author 霍中曦
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";
	
	public static final String CACHE_AUTH_INFO = "authInfo";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_MENU_LIST_PER = "menuListPer";
	public static final String CACHE_MENU_LIST_HYK = "menuListHyk";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
		}
		return user;
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}


	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(User user){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)CacheUtils.get(user.getId());
		Menu parent=new Menu();
		if (menuList == null){
				Menu m = new Menu();
				m.setUserId(user.getId());
				m.setId("1");
				menuList =getSubMenuTree(m);
				CacheUtils.put(user.getId(), menuList);
		}
		return menuList;
	}


	/**
	 * 获取菜单目录树
	 */
	public static List<Menu> getSubMenuTree(Menu menuNew) {
		List<Menu> allSubMenuList =getAllSubMenuList(menuNew);
		List<Menu> result = new ArrayList<Menu>();
		for (Menu menu : allSubMenuList) {
			if (!isChildMenu(menu).booleanValue()) {
				List<Menu> subMenuList = getSubMenuTree(menu);
				menu.setChildMenu(subMenuList);
			}
			result.add(menu);
		}
		return result;
	}
	private static Boolean isChildMenu(Menu curMenu) {
		List<Menu> curSubMenu = menuDao.findChildByParent(curMenu);
		if (curSubMenu.size() == 0) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	private static List<Menu> getAllSubMenuList(Menu menu) {
		List<Menu> curSubMenu = menuDao.findChildByParent(menu);
		return curSubMenu;
	}


	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============

	

	

	
}
