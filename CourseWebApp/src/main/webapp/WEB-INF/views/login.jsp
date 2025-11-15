<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<%
    String ctx = request.getContextPath();
    String msg = request.getParameter("msg");
    String err = request.getParameter("error");
%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Login - Course App</title>
  <link rel="stylesheet" href="<%=ctx%>/css/styles.css" />
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
</head>
<body>
  <div class="container">
    <div class="header">
      <div class="brand">
        <div class="logo">C</div>
        <div>
          <div class="title">Course Enrollment</div>
          <div style="font-size:12px;color:var(--muted)">Student Portal</div>
        </div>
      </div>
      <div class="nav">
        <a href="<%=ctx%>/courses">Courses</a>
        <a href="<%=ctx%>/register.jsp">Register</a>
      </div>
    </div>

    <div class="card">
      <h2 style="margin-bottom:8px">Student Login</h2>

      <% if (msg != null) { %>
        <p style="color:var(--success); margin-bottom:8px;"><%=URLEncoder.encode(msg, "UTF-8").replace("+", " ")%></p>
      <% } %>
      <% if (err != null) { %>
        <p style="color:var(--danger); margin-bottom:8px;">Login failed. Please check credentials.</p>
      <% } %>

      <form class="form" method="post" action="<%=ctx%>/auth">
        <input type="hidden" name="action" value="login" />
        <label>Username</label>
        <input class="input" name="username" required />
        <label>Password</label>
        <input class="input" name="password" type="password" required />
        <div style="display:flex; gap:8px; align-items:center; margin-top:8px;">
          <button class="btn" type="submit">Login</button>
          <a class="btn secondary" href="<%=ctx%>/register.jsp">Create account</a>
        </div>
      </form>
    </div>

    <p style="text-align:center; color:var(--muted); margin-top:18px;">© <%=java.time.Year.now()%> Course Enrollment</p>
  </div>
</body>
</html>
