package com.my.admin.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.admin.controller.BaseController;
import com.my.common.common.DataPage;
import com.my.common.common.PageResult;
import com.my.common.system.domain.Module;
import com.my.common.system.domain.Permission;
import com.my.common.system.service.ModuleService;
import com.my.common.system.service.PermissionService;


@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController<Module> {
	
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private PermissionService permissionService;
	
	
	@RequestMapping("/init")
	public String init(ModelMap modelMap) {
		return "/system/module/module";
	}
	
	@RequestMapping("/listModule")
	public void listModule(HttpServletResponse response,DataPage<Module> page, ModelMap modelMap) {
		PageResult<Module> result = moduleService.queryModuleList(page, null);
		returnPageJson(response,result.getPage());
	}
	
	@RequestMapping("/addModule")
	public String addModule(ModelMap modelMap) {
		
		return "/system/module/addModule";
	}
	
	@RequestMapping("/saveModule")
	public void saveModule(HttpServletResponse response,ModelMap modelMap, Module module) {
		try {
			Module searchModule = new Module();
			searchModule.setName(module.getName());
			List<Module> moduleList = moduleService.queryModuleList(module);
			if (moduleList != null && moduleList.size() > 0) {
				outJson(response,this.AJAX_FAIL,"模块已存在");
				return;
			}
			if (moduleService.saveModule(module) != 0) {
				outJson(response,this.AJAX_SUCCESS,"新增模块成功");
				return;
			}
		} catch (Exception e) {
			logger.error("saveModule 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/editModule")
	public Object editModule(ModelMap modelMap, Long id) {
		Module module = moduleService.queryModuleById(id);
		
		modelMap.put("editObj", module);
		return "/system/module/editModule";
	}
	
	@RequestMapping("/updateModule")
	public void updateModule(HttpServletResponse response,ModelMap modelMap, Module module) {
		try {
			Module searchModule = new Module();
			searchModule.setName(module.getName());
			List<Module> moduleList = moduleService.queryModuleList(searchModule);
			if (moduleList != null && moduleList.size() > 0 && 
					moduleList.get(0).getId().longValue() != module.getId().longValue()) {
				outJson(response,this.AJAX_FAIL,"模块已存在");
				return;
			}
			if (moduleService.updateModule(module) != 0) {
				outJson(response,this.AJAX_SUCCESS,"修改成功");
				return;
			}
		} catch (Exception e) {
			logger.error("updateModule 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/deleteModule")
	public void deleteModule(HttpServletResponse response,Long id) {
		try {
			Permission permission = new Permission();
			permission.setModuleId(id);
			List<Permission> permissionList = permissionService.queryPermissionList(permission);
			if(null!=permissionList && permissionList.size()>0){
				outJson(response,this.AJAX_FAIL,"该模块下配有菜单权限，请先删除菜单权限");
			}else{
				if (moduleService.deleteModuleById(id) != 0) {
					outJson(response,this.AJAX_SUCCESS,"删除成功");
					return;
				}
			}
		} catch (Exception e) {
			logger.error("deleteModule 异常",e);
			outJsonForException(response);
		}
		
	}
	
	// 模块菜单设置
	/*@RequestMapping("listMenuManage")
	public String listMenuManage(ModelMap modelMap) {
		List<Module> moduleList = moduleService.queryModuleList(null);
		modelMap.put("moduleList", moduleList);
		return "/system/listMenuManage";
	}
	
	@RequestMapping("saveModelMenu")
	public ModelAndView saveModelMenu(String[] idAndSortIndexes) {
		Module module = new Module();
		String[] result;
		for (String idAndSortIndex : idAndSortIndexes) {
			result = idAndSortIndex.split("_");
			module.setId(Long.valueOf(result[0]));
			module.setSortIndex(Integer.valueOf(result[1]));
			moduleService.updateModuleSortIndex(module);
		}
		return super.ajaxSuccess(super.UPDATE_SUCCESS, false);
	}
	
	@RequestMapping("listMenuPermission")
	public String listMenuPermission(ModelMap modelMap, Long moduleId) {
		Permission permission = new Permission();
		permission.setModuleId(moduleId);
		List<Permission> permissionList = permissionService.queryPermissionList(permission);
		modelMap.put("permissionList", permissionList);
		return "/system/listMenuPermission";
	}
	
	@RequestMapping("saveMenuPermission")
	public ModelAndView saveMenuPermission(String[] idAndSortIndexes) {
		Permission permission = new Permission();
		String[] result;
		for (String idAndSortIndex : idAndSortIndexes) {
			result = idAndSortIndex.split("_");
			permission.setId(Long.valueOf(result[0]));
			permission.setSortIndex(Integer.valueOf(result[1]));
			permissionService.updatePermissionSortIndex(permission);
		}
		return super.ajaxSuccess(super.UPDATE_SUCCESS);
	}*/
}
