package com.raj.ecommerce.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Integer paymentId;

	@NotNull(message = "Payment Amount is Mandatory and can't be Null is ")
	@Column(name = "payment_amount")
	private Double paymentAmount;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type")
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatus paymentStatus;

	@OneToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Orders order;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({
	    "password",
	    "orders",
	    "payments",
	    "reviews",
	    "cart",
	    "address"
	})
	private User user;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment(@NotNull(message = "Payment Amount is Mandatory and can't be Null is ") Double paymentAmount,
			LocalDateTime paymentDate, PaymentMethod paymentMethod, PaymentStatus paymentStatus, Orders order,
			User user) {
		super();
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.order = order;
		this.user = user;
	}

	public Payment() {

	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentAmount=" + paymentAmount + ", paymentDate=" + paymentDate
				+ ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + "]";
	}

}
