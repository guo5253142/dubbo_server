package com.my.admin.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.admin.common.Constants;
import com.my.admin.controller.BaseController;
import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.common.constant.UsedTag;
import com.my.common.system.domain.Role;
import com.my.common.system.domain.User;
import com.my.common.system.domain.constant.UserStatus;
import com.my.common.system.domain.vo.UserVo;
import com.my.common.system.service.ModuleService;
import com.my.common.system.service.PermissionItemService;
import com.my.common.system.service.PermissionService;
import com.my.common.system.service.RoleService;
import com.my.common.system.service.UserService;
import com.my.common.tools.MD5;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {

	private static final long serialVersionUID = 1L;
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/init")
	public String init(ModelMap modelMap) {
		List<Role> roleList = roleService.queryRoleList(null);
		modelMap.put("roleList", roleList);
		return "/system/user/user";
	}
	@RequestMapping("/listUser")
	public void listUser(UserVo vo,HttpServletResponse response, DataPage<User> page) {
		PageResult<User> result = userService.queryUser(page, vo);
		returnPageJson(response,result.getPage());
	}

	@RequestMapping("/addUser")
	public String addUser(ModelMap modelMap) {
		List<Role> roleList = roleService.queryRoleList(null);
		List<UserStatus> statusList=UserStatus.getAll(UserStatus.class);
		modelMap.put("roleList", roleList);
		modelMap.put("statusList", statusList);
		return "/system/user/addUser";
	}

	@RequestMapping("/saveUser")
	public void saveUser(HttpServletRequest request,HttpServletResponse response,User user) {
		try {
			if (userService.getUserByAccount(user.getAccount()) != null) {
				outJson(response,this.AJAX_FAIL,"用户名已存在");
			}else{
				user.setPassword(MD5.encode1(Constants.DEFAULT_PASSWD));
				user.setUsedTag(UsedTag.enabled.getIndex());
				user.setCreator(getUser(request).getUser().getId());
				user.setCreateDate(new Date());
				userService.saveUser(user);
				outJson(response,this.AJAX_SUCCESS,"添加用户成功");
			}
		} catch (Exception e) {
			logger.error("saveUser 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/editUser")
	public String editUser(Long id, ModelMap modelMap) {
		User user=new User();
		user.setId(id);
		user = userService.getUser(user);
		List<Role> roleList = roleService.queryRoleList(null);
		List<UserStatus> statusList=UserStatus.getAll(UserStatus.class);
		modelMap.put("roleList", roleList);
		modelMap.put("statusList", statusList);
		modelMap.put("user", user);
		return "/system/user/editUser";
	}

	@RequestMapping("/updateUser")
	public void updateUser(HttpServletResponse response,User user) {
		try {
			userService.saveEditUser(user);
			outJson(response,this.AJAX_SUCCESS,"更新用户信息成功");
		} catch (Exception e) {
			logger.error("updateUser 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/deleteUser")
	public void deleteUser(HttpServletResponse response,Long id) {
		try {
			if(id.longValue()!=this.ADMIN_ID){
				User user = new User();
				user.setId(id);
				userService.deleteUser(user);
				outJson(response,this.AJAX_SUCCESS,"删除用户成功");
			}else{
				outJson(response,this.AJAX_FAIL,"无法删除系统管理员");
			}
		} catch (Exception e) {
			logger.error("deleteUser 异常",e);
			outJsonForException(response);
		}
		
	}
	
}
