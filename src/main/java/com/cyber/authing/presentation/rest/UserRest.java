package com.cyber.authing.presentation.rest;

import com.cyber.application.controller.AuthingTokenController;
import com.cyber.authing.application.service.UserService;
import com.cyber.authing.domain.entity.User;
import com.cyber.authing.domain.request.CreateUserRequest;
import com.cyber.authing.domain.request.UpdateUserRequest;
import com.cyber.authing.domain.request.UserRequest;
import com.cyber.authing.domain.response.CountStatus;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.PagingData;
import com.cyber.domain.entity.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRest extends AuthingTokenController{

	private final UserService userService;

	@GetMapping("/user/search")
	public Response searchUser(@Valid UserRequest request) {
		DataResponse<PagingData<User>> response = new DataResponse<>();
        User  user = request.toEvent(request.getTenantCode());
		PagingData<User> userPage = userService.selectPage(user);
		response.setData(userPage);
		return response;
	}

	@GetMapping("/user/select")
	public Response selectUser(@Valid UserRequest request) {
		DataResponse<List<User>> response = new DataResponse<>();
        User  user = request.toEvent(request.getTenantCode());
		List<User> userPage = userService.selectList(user);
		response.setData(userPage);
		return response;
	}

	@GetMapping("/user/status/count")
	public Response selectEnterpriseStatusCount() {
		DataResponse<List<CountStatus>> response = new DataResponse<>();
		List<CountStatus> countStatus = userService.countStatus();
		response.setData(countStatus);
		return response;
	}


	@GetMapping("/user")
	public Response selectOneUser(@Valid IdRequest idRequest) {
		DataResponse<User> response = new DataResponse<>();

		User user = new User();
		user.setId(idRequest.getId());
        user.setTenantCode(idRequest.getTenantCode());
		user = userService.selectOne(user);

		response.setData(user);
		return response;
	}

	@PostMapping("/user")
	public Response saveUser(@RequestBody @Valid CreateUserRequest request) {
	    User  user = request.toEvent(getSessionId(),request.getTenantCode());

		int result = userService.save(user);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@PutMapping("/user")
	public Response updateUser(@RequestBody @Valid UpdateUserRequest request) {
	    User  user = request.toEvent(getSessionId(),request.getTenantCode());
		int result = userService.updateById(user);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}

	@DeleteMapping("/user")
	public Response deleteUser(@Valid IdRequest idRequest) {
		User user = new User();
		user.setId(idRequest.getId());

		user.setTenantCode(idRequest.getTenantCode());
		user.setUpdator(getSessionId());
        user.setUpdateTime(new Date());

		int result = userService.deleteById(user);
		if (result < 1) {
			return Response.fail(HttpResultCode.SERVER_ERROR);
		}
		return Response.success();
	}
}
