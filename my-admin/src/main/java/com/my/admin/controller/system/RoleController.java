package com.my.admin.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.admin.controller.BaseController;
import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Module;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.Role;
import com.my.common.system.domain.RolePermission;
import com.my.common.system.service.ModuleService;
import com.my.common.system.service.PermissionItemService;
import com.my.common.system.service.PermissionService;
import com.my.common.system.service.RolePermissionService;
import com.my.common.system.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {

	private static final long serialVersionUID = 1L;
	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private PermissionItemService permissionItemService;
	@Autowired
	private RolePermissionService rolePermissionService;
	

	@RequestMapping("/init")
	public String init(ModelMap modelMap) {
		return "/system/role/role";
	}
	@RequestMapping("/listRole")
	public void listRole(Role vo,HttpServletResponse response, DataPage<Role> page) {
		
		PageResult<Role> result = roleService.queryRoleList(page, vo);
		returnPageJson(response,result.getPage());
	}

	@RequestMapping("/addRole")
	public String addRole(ModelMap modelMap) {
		List<Module> moduleList = moduleService.queryModuleList(null);
		Map<Long, List<Permission>> modulePermissionMap = new HashMap<Long, List<Permission>>();
		List<Permission> permissionList = permissionService.queryPermissionList(null);
		for (Permission permission : permissionList) {
			List<Permission> lp = modulePermissionMap.get(permission.getModuleId());
			if (lp == null) {
				lp = new ArrayList<Permission>();
				lp.add(permission);
				modulePermissionMap.put(permission.getModuleId(), lp);
				continue;
			}
			lp.add(permission);
		}
		Map<Long, List<PermissionItem>> permissionAndItemMap = new HashMap<Long, List<PermissionItem>>();
		List<PermissionItem> permissionItemList = permissionItemService.queryPermissionItemList(null);
		for (PermissionItem permissionItem : permissionItemList) {
			List<PermissionItem> pi = permissionAndItemMap.get(permissionItem.getPermissionId());
			if (pi == null) {
				pi = new ArrayList<PermissionItem>();
				pi.add(permissionItem);
				permissionAndItemMap.put(permissionItem.getPermissionId(), pi);
				continue;
			}
			pi.add(permissionItem);
		}

		modelMap.put("moduleList", moduleList);
		modelMap.put("modulePermissionMap", modulePermissionMap);
		modelMap.put("permissionAndItemMap", permissionAndItemMap);
		return "/system/role/addRole";
	}

	@RequestMapping("/saveRole")
	public void saveRole(HttpServletRequest request,HttpServletResponse response,Role role,String[] permissionItem,
			String[] permissionIds) {
		try {
			List<Role> roleList = roleService.queryRoleList(role);
			if (roleList != null && roleList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"该角色名已存在");
				return;
			}
			long rid = roleService.saveRole(role);
			if (rid != 0) {
				RolePermission rolePermission = null;
				String[] permissionIdAndItemId = null;
				if (permissionItem != null) {
					for (String str : permissionItem) {
						permissionIdAndItemId = str.split("_");
						rolePermission = new RolePermission();
						rolePermission.setPermissionId(Long.valueOf(permissionIdAndItemId[0]));
						rolePermission.setPermissionItemId(Long.valueOf(permissionIdAndItemId[1]));
						rolePermission.setRoleId(rid);
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				if (permissionIds != null) {
					for (String pid : permissionIds) {
						rolePermission = new RolePermission();
						rolePermission.setPermissionId(Long.valueOf(pid));
						rolePermission.setRoleId(rid);
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				outJson(response,this.AJAX_SUCCESS,"添加角色成功");
				return;
			}
			outJson(response,this.AJAX_FAIL,"操作失败，请稍后再试！");
		} catch (NumberFormatException e) {
			logger.error("saveRole 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/editRole")
	public String editRole(Long id, ModelMap modelMap) {
		Role role = roleService.getRoleById(id);
		List<Module> moduleList = moduleService.queryModuleList(null);
		Map<Long, List<Permission>> modulePermissionMap = new HashMap<Long, List<Permission>>();
		// 查询角色已有权限
		List<RolePermission> rolePermission = rolePermissionService
				.queryRolePermissionListByRoleId(id);
		List<Long> pIdList = new ArrayList<Long>();
		List<Long> pItemIdList = new ArrayList<Long>();
		for (RolePermission rp : rolePermission) {
			if (rp.getPermissionId() != null) {
				if (!pIdList.contains(rp.getPermissionId())) {
					pIdList.add(rp.getPermissionId());
				}
			}
			if (rp.getPermissionItemId() != null) {
				if (!pItemIdList.contains(rp.getPermissionItemId())) {
					pItemIdList.add(rp.getPermissionItemId());
				}
			}
		}

		List<Permission> permissionList = permissionService.queryPermissionList(null);
		for (Permission permission : permissionList) {
			List<Permission> lp = modulePermissionMap.get(permission.getModuleId());
			if (pIdList.contains(permission.getId())) {
				permission.setIsChecked(true);
			}
			if (lp == null) {
				lp = new ArrayList<Permission>();
				lp.add(permission);
				modulePermissionMap.put(permission.getModuleId(), lp);
				continue;
			}
			lp.add(permission);
		}
		Map<Long, List<PermissionItem>> permissionAndItemMap = new HashMap<Long, List<PermissionItem>>();
		List<PermissionItem> permissionItemList = permissionItemService
				.queryPermissionItemList(null);
		for (PermissionItem permissionItem : permissionItemList) {
			List<PermissionItem> pi = permissionAndItemMap.get(permissionItem.getPermissionId());
			if (pItemIdList.contains(permissionItem.getId())) {
				permissionItem.setIsChecked(true);
			}
			if (pi == null) {
				pi = new ArrayList<PermissionItem>();
				pi.add(permissionItem);
				permissionAndItemMap.put(permissionItem.getPermissionId(), pi);
				continue;
			}
			pi.add(permissionItem);
		}

		modelMap.put("moduleList", moduleList);
		modelMap.put("modulePermissionMap", modulePermissionMap);
		modelMap.put("permissionAndItemMap", permissionAndItemMap);
		modelMap.put("role", role);
		return "/system/role/editRole";
	}

	@RequestMapping("/updateRole")
	public void updateRole(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap, Role role, String[] permissionItem,
			String[] permissionIds) {
		try {
			Role r = new Role();
			r.setName(role.getName());
			List<Role> roleList = roleService.queryRoleList(r);
			if (roleList != null && roleList.size() > 0
					&& roleList.get(0).getId().longValue()!=role.getId().longValue()) {
				outJson(response,this.AJAX_FAIL,"该角色名已存在");
				return;
			}
			if (roleService.updateRole(role) != 0) {
				RolePermission rolePermission = null;
				RolePermission rPermission = new RolePermission();
				// 删除旧数据
				rPermission.setRoleId(role.getId());
				rolePermissionService.deleteRolePermission(rPermission);
				// 添加新数据
				if (permissionItem != null) {
					String[] moduleIdpermissionIdAndItemId = null;
					for (String str : permissionItem) {
						moduleIdpermissionIdAndItemId = str.split("_");
						rolePermission = new RolePermission();
						rolePermission.setPermissionId(Long.valueOf(moduleIdpermissionIdAndItemId[0]));
						rolePermission.setPermissionItemId(Long
								.valueOf(moduleIdpermissionIdAndItemId[1]));
						rolePermission.setRoleId(role.getId());
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				if (permissionIds != null) {
					for (String pid : permissionIds) {
						rolePermission = new RolePermission();
						rolePermission.setPermissionId(Long.valueOf(pid));
						rolePermission.setRoleId(role.getId());
						rolePermissionService.saveRolePermission(rolePermission);
					}
				}
				outJson(response,this.AJAX_SUCCESS,"修改角色成功");
				return;
			}
			outJson(response,this.AJAX_FAIL,"操作失败，请稍后再试！");
		} catch (NumberFormatException e) {
			logger.error("updateRole 异常",e);
			outJsonForException(response);
		}
	}

	@RequestMapping("/deleteRole")
	public void deleteRole(HttpServletResponse response,Long id) {
		try {
			if(id.longValue()!=this.ADMIN_ROLE_ID){
				// 删除item
				RolePermission rolePermission = new RolePermission();
				rolePermission.setRoleId(id);
				rolePermissionService.deleteRolePermission(rolePermission);
				roleService.deleteRole(id);
				outJson(response,this.AJAX_SUCCESS,"删除角色成功");
			}else{
				outJson(response,this.AJAX_FAIL,"无法删除系统管理员角色");
			}
		} catch (Exception e) {
			logger.error("deleteRole 异常",e);
			outJsonForException(response);
		}
		
	}
	
}
