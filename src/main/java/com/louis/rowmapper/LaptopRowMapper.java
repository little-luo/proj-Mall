package com.louis.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.louis.module.Laptop;

public class LaptopRowMapper implements RowMapper<Laptop> {

	@Override
	public Laptop mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Laptop laptop = new Laptop();
		
		laptop.setLaptopId(rs.getInt("laptop_id"));
		laptop.setLaptopName(rs.getString("laptop_name"));
		laptop.setPrice(rs.getInt("price"));
		laptop.setImageUrl(rs.getString("image_url"));
		
		return laptop;
	}
	
}
