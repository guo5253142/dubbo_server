package com.my.admin.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.my.admin.controller.BaseController;
import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.common.constant.YesOrNoType;
import com.my.common.system.domain.Module;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.domain.RolePermission;
import com.my.common.system.service.ModuleService;
import com.my.common.system.service.PermissionItemService;
import com.my.common.system.service.PermissionService;
import com.my.common.system.service.RolePermissionService;


@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission> {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private PermissionItemService permissionItemService;
	
	@RequestMapping("/init")
	public String init(ModelMap modelMap) {
		List<Module> moduleList = moduleService.queryModuleList(null);
		modelMap.put("moduleList", moduleList);
		return "/system/permission/permission";
	}
	
	@RequestMapping("/listPermission")
	public void listPermission(HttpServletResponse response,DataPage<Permission> page, Permission permission) {
		if (page == null) {
			page = new DataPage<Permission>();
		}
		PageResult<Permission> result = permissionService.queryPermissionList(page, permission);
		returnPageJson(response,result.getPage());
	}
	
	@RequestMapping("/addPermission")
	public String addPermission(ModelMap modelMap) {
		List<Module> moduleList = moduleService.queryModuleList(null);
		List<YesOrNoType> ynList= YesOrNoType.getAll(YesOrNoType.class);
		modelMap.put("ynList", ynList);
		modelMap.put("moduleList", moduleList);
		
		return "system/permission/addPermission";	
	}
	
	@RequestMapping("/savePermission")
	public void savePermission(HttpServletResponse response,Permission permission, ModelMap modelMap) {
		try {
			if (permissionService.savePermission(permission) != 0) {
				outJson(response,this.AJAX_SUCCESS,"新增成功");
				return;
			}
		} catch (Exception e) {
			logger.error("savePermission 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/editPermission")
	public String editPermission(ModelMap modelMap, Long id) {
		List<Module> moduleList = moduleService.queryModuleList(null);
		Permission permission = permissionService.queryPermissionById(id);
		List<YesOrNoType> ynList= YesOrNoType.getAll(YesOrNoType.class);
		modelMap.put("ynList", ynList);
		modelMap.put("moduleList", moduleList);
		modelMap.put("permission", permission);
		return "system/permission/editPermission";	
	}
	
	@RequestMapping("/updatePermission")
	public void updatePermission(HttpServletResponse response,Permission permission, ModelMap modelMap) {
		try {
			if (permissionService.updatePermission(permission) != 0) {
				outJson(response,this.AJAX_SUCCESS,"修改成功");
				return;
			}
		} catch (Exception e) {
			logger.error("updatePermission 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/deletePermission")
	public void deletePermission(HttpServletResponse response,Long id) {
		try {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(id);
			List<RolePermission> rolePermissionList = rolePermissionService.queryRolePermissionList(rolePermission);
			if (rolePermissionList != null && rolePermissionList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"已存在引用依赖");
				return;
			}
			PermissionItem permissionItem = new PermissionItem();
			permissionItem.setPermissionId(id);
			List<PermissionItem> permissioItemList = permissionItemService.queryPermissionItemList(permissionItem);
			if (permissioItemList != null && permissioItemList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"已存在引用依赖");
				return;
			}
			if (permissionService.deletePermission(id)!= 0) {
				outJson(response,this.AJAX_SUCCESS,"删除成功");
				return;
			}
		} catch (Exception e) {
			logger.error("deletePermission 异常",e);
			outJsonForException(response);
		}
		
	}
	
	
}
