<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <body>
<table>
  <tr>
    <th>Left</th>
    <th>Right</th>
  </tr>
  <tr>
    <td>${request.getAttribute("left")}</td>
    <td>${right}</td>
  </tr>
</table>
  </body>
</html>

