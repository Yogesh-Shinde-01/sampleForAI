package com.practice_security.entities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "user_table")
public class User implements UserDetails {

	@Id
	private String userId;

	private String name;

	private String email;

	private String password;

	private String about;

	@Column(name = "role")
	private String role;

//	// code change
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//	}

	// Other fields and methods...

	private Collection<? extends GrantedAuthority> authorities;

	// Getter and setter for authorities
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {

		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}