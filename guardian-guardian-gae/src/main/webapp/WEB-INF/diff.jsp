<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>
Diff
</title>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
    
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.js"></script>
    

  </head>

  <body>
<table class="guplicate-diff">
  
  
  <tr>
  <th class="foofoo">Field</th>
    <th class="original-content">Left</th>
    <th class="diff">Diff</th>
    <th class="original-content">Right</th>
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
     <script type="text/javascript">                                         
   $('th.original-content').click(function() {
                $('td:nth-child(3),th:nth-child(3)').hide();
            });
            $('th.foofoo').click(function() {
                $('td:nth-child(3),th:nth-child(3)').show();
            });
            
      $('th.diff').click(function() {
                $('td:nth-child(2),th:nth-child(2)').hide();
            });
            $('th.foofoo').click(function() {
                $('td:nth-child(2),th:nth-child(2)').show();
            });
 </script>
  </body>
</html>

