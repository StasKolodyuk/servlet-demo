package com.sample;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(
        name = "temperatureServlet",
        urlPatterns = "/temperature"
)
public class TemperatureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Temperature> temperatures = readFile();

        double average = temperatures.stream()
                                     .mapToInt(Temperature::getTemperature)
                                     .average()
                                     .getAsDouble();

        long daysAboveAverage = temperatures.stream()
                                           .filter(temperature -> temperature.getTemperature() > average)
                                           .count();

        long daysBelowZero = temperatures.stream()
                                         .filter(temperature -> temperature.getTemperature() < 0)
                                         .count();

        List<String> warmestDays = temperatures.stream()
                                               .sorted(Comparator.comparing(Temperature::getTemperature).reversed())
                                               .map(Temperature::getDate)
                                               .collect(Collectors.toList())
                                               .subList(0, 3);


        req.setAttribute("temperatures", temperatures);
        req.setAttribute("average", average);
        req.setAttribute("daysAboveAverage", daysAboveAverage);
        req.setAttribute("daysBelowZero", daysBelowZero);
        req.setAttribute("warmestDays", warmestDays);

        RequestDispatcher view = req.getRequestDispatcher("temperature.jsp");
        view.forward(req, resp);
    }

    public List<Temperature> readFile() {
        try {
            List<String> temperaturesString = Files.readAllLines(Paths.get(this.getClass().getClassLoader().getResource("temperature.txt").toURI()));

            List<Temperature> temperatures = new ArrayList<>();

            for (String temperatureString : temperaturesString) {
                String[] parts = temperatureString.split(" ");
                String date = parts[0];
                int temp = Integer.parseInt(parts[1]);

                temperatures.add(new Temperature(date, temp));
            }

            return temperatures;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
