package model;

public class Admin {
	private Long id;
	private String password;
	private String name;
	private Long age;
	private String sex;

	public Admin() {
		super();
	}

	public Admin(Long id, String password, String name, Long age, String sex) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
