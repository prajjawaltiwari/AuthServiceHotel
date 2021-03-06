package com.hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.model.AuthResponse;
import com.hotel.model.HotelUsers;
import com.hotel.model.UserLoginCredential;
import com.hotel.model.UserToken;
import com.hotel.repository.UserRepository;
import com.hotel.service.CustomerDetailsService;
import com.hotel.service.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private CustomerDetailsService custdetailservice;
	@Autowired
	private UserRepository urepo;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserLoginCredential userlogincredentials) {
		log.debug("Start : {}", "login");
		final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUid());
		if (userdetails.getPassword().equals(userlogincredentials.getPassword())) {
			log.debug("End : {}", "login");
			return new ResponseEntity<>(
					new UserToken(userlogincredentials.getUid(), jwtutil.generateToken(userdetails)), HttpStatus.OK);
		} else {
			log.debug("Access Denied : {}", "login");
			return new ResponseEntity<>("Invalid Username or Password", HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<?> getValidity(@RequestHeader("Authorization") String token) {
		log.debug("Start : {}", "getValidity");
		token = token.substring(7);
		AuthResponse res = new AuthResponse();
		if (jwtutil.validateToken(token)) {
			res.setUid(jwtutil.extractUsername(token));
			res.setValid(true);
			res.setName((urepo.findById(jwtutil.extractUsername(token)).orElse(null).getUname()));
			HotelUsers auth1=urepo.findById(jwtutil.extractUsername(token)).orElseThrow(RuntimeException::new);
			res.setRole(auth1.getRole());

		} else
			res.setValid(false);
		log.debug("AuthResponse : {}", res);
		log.debug("End : {}", "getValidity");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
