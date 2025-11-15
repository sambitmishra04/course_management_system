<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctx = request.getContextPath();
%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Register - Course App</title>
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
        <a href="<%=ctx%>/login.jsp">Login</a>
      </div>
    </div>

    <div class="card">
      <h2 style="margin-bottom:8px">Create Student Account</h2>

      <form class="form" method="post" action="<%=ctx%>/auth">
        <input type="hidden" name="action" value="register" />
        <label>Username</label>
        <input class="input" name="username" required />
        <label>Password</label>
        <input class="input" name="password" type="password" required />
        <label>Full name</label>
        <input class="input" name="full_name" required />
        <label>Email</label>
        <input class="input" name="email" type="email" />
        <label>Phone</label>
        <input class="input" name="phone" />
        <div style="display:flex; gap:8px; margin-top:8px;">
          <button class="btn" type="submit">Register</button>
          <a class="btn secondary" href="<%=ctx%>/login.jsp">Already have account</a>
        </div>
      </form>
    </div>

    <p style="text-align:center; color:var(--muted); margin-top:18px;">© <%=java.time.Year.now()%> Course Enrollment</p>
  </div>
</body>
</html>
