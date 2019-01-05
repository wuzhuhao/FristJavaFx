package model;

import java.text.DecimalFormat;

public class Goods {
	private Long id;
	private String name;
	private Double price;
	private Long weight;
	private String component;
	private String sources;
	private String apply;
	private String type;
	private Long praise;
	private Long commonly;
	private Long negativecomment;
	private Long num;

	public Goods() {
		super();
	}

	public Goods(Long id, String name, Double price, Long weight,
			String component, String sources, String apply, String type,
			Long praise, Long commonly, Long negativecomment, Long num) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.component = component;
		this.sources = sources;
		this.apply = apply;
		this.type = type;
		this.praise = praise;
		this.commonly = commonly;
		this.negativecomment = negativecomment;
		this.num = num;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPraise() {
		return praise;
	}

	public void setPraise(Long praise) {
		this.praise = praise;
	}

	public Long getCommonly() {
		return commonly;
	}

	public void setCommonly(Long commonly) {
		this.commonly = commonly;
	}

	public Long getNegativecomment() {
		return negativecomment;
	}

	public void setNegativecomment(Long negativecomment) {
		this.negativecomment = negativecomment;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getPraiseString() {
		DecimalFormat df = new DecimalFormat("0.00");
		if (praise.equals((long) 0) && commonly.equals((long) 0)
				&& negativecomment.equals((long) 0)) {
			return df.format(0 * 100) + "%";
		}
		double dd = (double) ((double) praise / (double) (praise + commonly + negativecomment));
		return df.format(dd * 100) + "%";
	}

	public String getCommonlyString() {
		DecimalFormat df = new DecimalFormat("0.00");
		if (praise.equals((long) 0) && commonly.equals((long) 0)
				&& negativecomment.equals((long) 0)) {
			return df.format(0 * 100) + "%";
		}
		double dd = (double) ((double) commonly / (double) (praise + commonly + negativecomment));
		return df.format(dd * 100) + "%";
	}

	public String getNegativecommentString() {
		DecimalFormat df = new DecimalFormat("0.00");
		if (praise.equals((long) 0) && commonly.equals((long) 0)
				&& negativecomment.equals((long) 0)) {
			return df.format(0 * 100) + "%";
		}
		double dd = (double) ((double) negativecomment / (double) (praise
				+ commonly + negativecomment));
		return df.format(dd * 100) + "%";
	}

}
