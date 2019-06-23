package io.tomahawkd.pki.controller.user;

import io.tomahawkd.pki.exceptions.NotFoundException;
import io.tomahawkd.pki.model.SystemLogModel;
import io.tomahawkd.pki.model.UserInfoModel;
import io.tomahawkd.pki.service.SystemLogService;
import io.tomahawkd.pki.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserInfoController {

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private SystemLogService systemLogService;

	// http://127.0.0.1/user/info/liucheng
	@GetMapping("/info/{user}")
	public String getInfoPageById(@PathVariable String user) throws NotFoundException {
		systemLogService.insertLogRecord(UserInfoController.class.getName(),
				"getInfo", SystemLogModel.INFO, "Accept username: " + user);
		UserInfoModel model = userInfoService.getUserInfo(user);
		if (model == null) {
			systemLogService.insertLogRecord(UserInfoController.class.getName(), "getInfo",
							SystemLogModel.FATAL, "User not found");
			throw new NotFoundException("User not found");
		}
		return model.toString();
	}
}