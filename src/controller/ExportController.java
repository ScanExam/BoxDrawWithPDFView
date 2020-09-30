package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ExportController {
	
	public LinkedList<coord> coords;
	
	public ExportController() {
		coords = new LinkedList<coord>();
	}

	public class coord {
		public coord() {
			relativeX = 0;
			relativeY = 0;
			relativeWidth = 0;
			relativeHeight = 0;
		}

		public coord(double x, double y, double width, double height) {
			relativeX = x;
			relativeY = y;
			relativeWidth = width;
			relativeHeight = height;
		}

		public String toString() {
			return relativeX + "," + relativeY + "," + relativeWidth + "," + relativeHeight;

		}

		public double relativeX;
		public double relativeY;
		public double relativeWidth;
		public double relativeHeight;
	}

	

	public void add(double x, double y, double width, double height) {
		coords.add(new coord(x, y, width, height));
		String s = "Added x : " + Math.floor(x * 100) + " y : " + Math.floor(y * 100) + " Width : "
				+ Math.floor(width * 100) + " Height : " + Math.floor(height * 100);
		System.out.println(s);
	}

	public List<String> coordsToStringList() {
		List<String> s = new LinkedList<String>();
		for (coord c : coords) {
			s.add(c.toString());
		}
		return s;
	}

	public void stringListToCoords(List<String> s) {
		for (String x : s) {
			String[] split = x.split(",");
			coord c = new coord();
			c.relativeX = Double.parseDouble(split[0]);
			c.relativeY = Double.parseDouble(split[1]);
			c.relativeWidth = Double.parseDouble(split[2]);
			c.relativeHeight = Double.parseDouble(split[3]);
			coords.add(c);
		}
	}

	public void save() {
		Path file = Paths.get("export.txt");
		try {
			Files.write(file, coordsToStringList(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void load() {
		Path file = Paths.get("export.txt");
		try {
			List<String> s = Files.readAllLines(file);
			stringListToCoords(s);
			System.out.println("Finished Loading");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
