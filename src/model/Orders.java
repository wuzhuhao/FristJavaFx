package model;

public class Orders {
	private Long id;
	private Long g_id;
	private Long u_id;
	private Long num;
	private Double total;
	private Long status;
	private String g_name;

	public Orders() {
		super();
	}

	public Orders(Long id, Long g_id, Long u_id, Long num, Double total,
			Long status) {
		super();
		this.id = id;
		this.g_id = g_id;
		this.u_id = u_id;
		this.num = num;
		this.total = total;
		this.status = status;
	}

	public String getG_name() {
		return g_name;
	}

	public void setG_name(String g_name) {
		this.g_name = g_name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getG_id() {
		return g_id;
	}

	public void setG_id(Long g_id) {
		this.g_id = g_id;
	}

	public Long getU_id() {
		return u_id;
	}

	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getStatusString() {
		String str = "";
		if (status == 0) {
			str = "Î´ÆÀ¼Û";
		} else {
			str = "ÒÑÆÀ¼Û";
		}
		return str;
	}
}
