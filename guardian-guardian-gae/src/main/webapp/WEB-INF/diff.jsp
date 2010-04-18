<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <body>
<table>
  <tr>
    <th>Left</th>
    <th>Right</th>
  </tr>

	<c:forEach items="${diffs}" var="diff">
	      <tr>
		<td>
			<td>${diff.left}</td>
			<td>${diff.right}</td>
		</td>
	      </tr>
    </c:forEach>
</table>

  </body>
</html>

