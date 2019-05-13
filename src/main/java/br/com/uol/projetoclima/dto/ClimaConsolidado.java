package br.com.uol.projetoclima.dto;

public class ClimaConsolidado {
	
	private Long id;
	private String weather_state_name;
	private String weather_state_abbr;
	private String wind_direction_compass;
	private String created;
	private String applicable_date;
	private double min_temp;
	private double max_temp;
	private double the_temp;
	private double wind_speed;
	private double wind_direction;
	private double air_pressure;
	private int humidity;
	private double visibility;
	private int predictability;
	
	public Long getId() {
		return id;
	}
	public String getWeather_state_name() {
		return weather_state_name;
	}
	public String getWeather_state_abbr() {
		return weather_state_abbr;
	}
	public String getWind_direction_compass() {
		return wind_direction_compass;
	}
	public String getCreated() {
		return created;
	}
	public String getApplicable_date() {
		return applicable_date;
	}
	public double getMin_temp() {
		return min_temp;
	}
	public double getMax_temp() {
		return max_temp;
	}
	public double getThe_temp() {
		return the_temp;
	}
	public double getWind_speed() {
		return wind_speed;
	}
	public double getWind_direction() {
		return wind_direction;
	}
	public double getAir_pressure() {
		return air_pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public double getVisibility() {
		return visibility;
	}
	public int getPredictability() {
		return predictability;
	}
	
	


}
