<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>
Diff
</title>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>

  <body>
<table>
  <COL width="1" />
  <COL />
  <COL width="1" />
  
  <tr>
  <th>Field</th>
    <th>Left</th>
    <th>Diff</th>
    <th>Right</th>
  </tr>

	<c:forEach items="${diffs}" var="diff">
	      <tr>
	        <th>${diff.fieldName}</th>
			<td>${diff.left}</td>
			<td>${diff.diff}</td>
			<td>${diff.right}</td>
	      </tr>
    </c:forEach>
</table>

  </body>
</html>

