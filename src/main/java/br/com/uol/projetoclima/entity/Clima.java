package br.com.uol.projetoclima.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
@Table(name = "t_clima_cliente")
public class Clima {
	
	public Clima(String data, String cidade, double temp_max,double temp_min, Cliente cliente) {
		this.data = data;
		this.cidade = cidade;
		this.temp_max = temp_max;
		this.temp_min = temp_min;
		this.cliente = cliente;
	}
	
	public Clima() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String data;

	private String cidade;

	private double temp_max;

	private double temp_min;
	
	@JoinColumn(name = "id_cliente")
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Cliente cliente;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public double getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}

	public double getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(temp_max);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(temp_min);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Clima other = (Clima) obj;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(temp_max) != Double.doubleToLongBits(other.temp_max))
			return false;
		if (Double.doubleToLongBits(temp_min) != Double.doubleToLongBits(other.temp_min))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Clima [getData()=" + getData() + ", getCidade()=" + getCidade() + ", getTemp_max()=" + getTemp_max()
				+ ", getTemp_min()=" + getTemp_min() + ", getCliente()=" + getCliente() + "]";
	}





}
