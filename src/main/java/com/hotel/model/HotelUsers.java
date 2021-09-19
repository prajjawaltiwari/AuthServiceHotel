package com.hotel.model;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelUsers {

	@Id
	@Column(name = "userid", length = 20)
	private String userid;
	@Column(name = "upassword", length = 20)
	private String upassword;
	@Column(name = "uname", length = 20)
	private String uname;
	@Column(name = "role")
	private String role;

	@Override
	public String toString() {
		return "UserHospital [userid=" + userid + ", upassword=" + upassword + ", uname=" + uname + ", role=" + role
				+ "]";
	}

}
