<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
    table {
        border-collapse: collapse;
    }

    table, th, td {
        border: 1px solid black;
    }
</style>
<h1>Temperature</h1>
<table>
    <tr>
        <th>Date</th>
        <th>Temperature</th>
    </tr>
    <c:forEach items="${temperatures}" var="temperature">
        <tr>
            <td>${temperature.date}</td>
            <td>${temperature.temperature}</td>
        </tr>
    </c:forEach>
</table>
<br/>
<div>
    <div>Average Temperature: ${average}</div>
    <div>Days Above Average: ${daysAboveAverage}</div>
    <div>Days Below Zero: ${daysBelowZero}</div>
    <div>Warmest Days: <c:forEach items="${warmestDays}" var="day"><span>${day} </span></c:forEach>
    </div>
</div>