package com.my.admin.controller.system;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.my.admin.controller.BaseController;
import com.my.common.common.DataPage;
import com.my.common.system.domain.Module;
import com.my.common.system.domain.Permission;
import com.my.common.system.domain.PermissionItem;
import com.my.common.system.service.PermissionItemService;


@Controller
@RequestMapping("/permissionItem")
public class PermissionItemController extends BaseController<PermissionItem> {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private PermissionItemService permissionItemService;
	
	@RequestMapping("/init")
	public String init(ModelMap modelMap,Long pid) {
		modelMap.put("pid", pid);
		return "/system/permissionItem/permissionItem";
	}
	
	@RequestMapping("/listPermissionItem")
	public void listPermissionItem(HttpServletResponse response,DataPage<PermissionItem> page,Long pid) {
		PermissionItem pItem = new PermissionItem();
		pItem.setPermissionId(pid);
		List<PermissionItem> permissionItemList = permissionItemService.queryPermissionItemList(pItem);
		page.setDataList(permissionItemList);
		page.setTotalCount(permissionItemList.size());
		returnPageJson(response,page);
	}
	
	@RequestMapping("/addPermissionItem")
	public String addPermissionItem(ModelMap modelMap, Long pid) {
		modelMap.put("pid", pid);
		return "/system/permissionItem/addPermissionItem";
	}
	
	@RequestMapping("/savePermissionItem")
	public void savePermissionItem(HttpServletResponse response,PermissionItem permissionItem) {
		try {
			if (StringUtils.isNotBlank(permissionItem.getName())) {
				PermissionItem item = new PermissionItem();
				item.setName(permissionItem.getName());
				item.setPermissionId(permissionItem.getPermissionId());
				List<PermissionItem> itemList = permissionItemService.queryPermissionItemList(item);
				if (itemList != null && itemList.size() > 0) {
					outJson(response,this.AJAX_FAIL,"子权限已存在");
					return;
				}
			}
			if (permissionItemService.savePermissionItem(permissionItem) != 0) {
				outJson(response,this.AJAX_SUCCESS,"新增成功");
				return;
			}
		} catch (Exception e) {
			logger.error("savePermissionItem 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/editPermissionItem")
	public Object editPermissionItem(ModelMap modelMap, Long id) {
		PermissionItem permissionItem = permissionItemService.getPermissionItemById(id);
		
		modelMap.put("permissionItem", permissionItem);
		return "/system/permissionItem/editPermissionItem";
	}
	
	@RequestMapping("/updatePermissionItem")
	public void updatePermissionItem(HttpServletResponse response,PermissionItem permissionItem) {
		try {
			if (StringUtils.isNotBlank(permissionItem.getName())) {
				PermissionItem item = new PermissionItem();
				item.setName(permissionItem.getName());
				item.setPermissionId(permissionItem.getPermissionId());
				List<PermissionItem> itemList = permissionItemService.queryPermissionItemList(item);
				if (itemList != null && itemList.size() > 0 
						&& itemList.get(0).getId().intValue() != permissionItem.getId().intValue()) {
					outJson(response,this.AJAX_FAIL,"子权限已存在");
					return;
				}
			}
			if (permissionItemService.updatePermissionItem(permissionItem) != 0) {
				outJson(response,this.AJAX_SUCCESS,"修改成功");
				return;
			}
		} catch (Exception e) {
			logger.error("updatePermissionItem 异常",e);
			outJsonForException(response);
		}
	}
	
	@RequestMapping("/deletePermissionItem")
	public void delPermissionItem(HttpServletResponse response,String ids) {
		try {
			List<String> idList=Arrays.asList(ids.split(","));
			if (permissionItemService.delPermissionItemByIds(idList) != 0) {
				outJson(response,this.AJAX_SUCCESS,"删除成功");
				return;
			}
		} catch (Exception e) {
			logger.error("delPermissionItem 异常",e);
			outJsonForException(response);
		}
	}
}
