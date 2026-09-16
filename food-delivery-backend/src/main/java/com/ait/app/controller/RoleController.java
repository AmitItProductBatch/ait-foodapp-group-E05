package com.ait.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.model.Role;
import com.ait.app.requestbody.RoleRequestDto;
import com.ait.app.requestbody.RoleResponseDto;
import com.ait.app.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	RoleService rs;

	@PostMapping("/add")
	public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto dto){
		RoleResponseDto rdto = rs.addRole(dto);
		return new ResponseEntity(rdto, HttpStatus.CREATED);
	}
	
	@PostMapping("/assign/{userId}/{roleId}")
    public ResponseEntity<String> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        rs.assignRoleToUser(userId, roleId);
        return new ResponseEntity("Role assigned successfully", HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) {
        RoleResponseDto role = rs.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
	
	 @GetMapping("/get")
	    public ResponseEntity getAllRoles() {
	        List<Role> r = rs.getAllRoles();
	        return new ResponseEntity<>(r, HttpStatus.OK);
	    }
	 
	 @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
	        String msg = rs.deleteRole(id);
	        return new ResponseEntity<>(msg, HttpStatus.OK);
	    }
	 
	    @PatchMapping("/update/{id}")
	    public Role updateRole(@PathVariable Long id, @RequestBody Role r) {
	        return rs.roleUpdate(id, r);
	    }

}
