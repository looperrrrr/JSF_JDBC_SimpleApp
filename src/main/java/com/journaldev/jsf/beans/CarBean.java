package com.journaldev.jsf.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CarBean implements Serializable {


	public List<Car> getCars() throws ClassNotFoundException, SQLException {

		Connection connect = null;

		//String url = "jdbc:postgresql://192.168.1.224:5432/test";

		//String username = "postgres";
		//String password = "123";

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			//connect = DriverManager.getConnection("jdbc:postgresql://192.168.1.224:5432/test", "postgres", "123");
			
			connect = DriverManager.getConnection("jdbc:oracle:thin:@orcldb:1521:orcl", "scott", "123");
			// System.out.println("Connection established"+connect);

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}

		List<Car> cars = new ArrayList<Car>();
		PreparedStatement pstmt = connect
				.prepareStatement("select car_id, cname, color, speed, Manufactured_Country from Car");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			Car car = new Car();
			car.setCid(rs.getInt("car_id"));
			car.setCname(rs.getString("cname"));
			car.setColor(rs.getString("color"));
			car.setSpeed(rs.getInt("speed"));
			car.setMfdctry(rs.getString("Manufactured_Country"));

			cars.add(car);

		}

		// close resources
		rs.close();
		pstmt.close();
		connect.close();

		return cars;

	}

}
