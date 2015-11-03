package ch.approppo.recyclerview;

import java.io.Serializable;

/**
 * Created by Martin Neff @approppo GmbH on 01.11.15.
 */
public class DataObject implements Serializable{

	enum Sex { MALE, FEMALE}

	private String name;

	private Sex sex;

	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
