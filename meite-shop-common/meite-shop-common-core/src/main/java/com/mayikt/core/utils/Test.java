package com.mayikt.core.utils;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class User {
	private String name;
	
	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
	
}

public class Test {

	public static void main(String[] args) {
		Assert.notNull("aa", "aa");
		List<User> list = new ArrayList<User>(2); 
		list.add(new User("robbie")); 
		list.add(new User("nick")); 
		
		Object[] array2 = list.toArray();
		for (int i = 0; i < array2.length; i++) {
			User object = (User) array2[i];
			System.out.println(object.toString());
		}
		
		User[] array = new User[list.size()]; 
		array = list.toArray(array);
		
		
		Set<User> set = new HashSet<User>();
		set.add(new User("robbie"));
		set.add(new User("robbie"));
		System.out.println(set.size());
	}
	
}
