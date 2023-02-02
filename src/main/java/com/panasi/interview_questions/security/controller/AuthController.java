package com.panasi.interview_questions.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panasi.interview_questions.payload.MessageResponse;
import com.panasi.interview_questions.security.jwt.JwtUtils;
import com.panasi.interview_questions.security.payload.ERole;
import com.panasi.interview_questions.security.payload.JwtResponse;
import com.panasi.interview_questions.security.payload.SignInRequest;
import com.panasi.interview_questions.security.payload.SignUpRequest;
import com.panasi.interview_questions.security.repository.RoleRepository;
import com.panasi.interview_questions.security.repository.UserRepository;
import com.panasi.interview_questions.security.repository.entity.Role;
import com.panasi.interview_questions.security.repository.entity.User;
import com.panasi.interview_questions.security.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		
		return new ResponseEntity<>(new JwtResponse(jwt, 
				 userDetails.getId(), 
				 userDetails.getUsername(), 
				 userDetails.getEmail(), 
				 roles), HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			String message = "Error: Username is already taken!";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			String message = "Error: Email is already in use!";
			return new ResponseEntity<>(new MessageResponse(message), HttpStatus.BAD_REQUEST);
		}

		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER);
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
					roles.add(adminRole);
					break;
					
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER);
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		String message = "User registered successfully!";
		return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
	}

}
