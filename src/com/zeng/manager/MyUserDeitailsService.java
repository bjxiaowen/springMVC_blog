//package com.zeng.manager;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.zeng.dao.User_infoDao;
//import com.zeng.entity.User_info;
//import com.zeng.entity.User_role;
//
//
//public class MyUserDeitailsService implements UserDetailsService{
//
//	@Autowired
//	private User_infoDao user_infoDao;
//	
//	@Override
//	public UserDetails loadUserByUsername(String userName)
//			throws UsernameNotFoundException, DataAccessException {
//		User_info user = user_infoDao.getUserByUserName(userName);
//		 List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
//		    return buildUserForAuthentication(user, authorities);
//	}
//	 /**
//	   * 返回验证角色
//	   * @param userRoles
//	   * @return
//	   */
//	  private List<GrantedAuthority> buildUserAuthority(Set<User_role> userRoles){
//	    Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
//	    for(User_role userRole:userRoles){
//	      setAuths.add(new SimpleGrantedAuthority(userRole.getRole().getRoleId().toString()));
//	    }
//	    List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
//	    return result;
//	  }
//	   
//	  /**
//	   * 返回验证用户
//	   * @param user
//	   * @param authorities
//	   * @return
//	   */
//	  private User buildUserForAuthentication(CwSysUser user,List<GrantedAuthority> authorities){
//	    return new User(user.getUserNo(),user.getPassword(),true,true,true,true,authorities);
//	  }
//	   
//}
