<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.course.model.EnrollmentDTO" %>
<%
  String ctx = request.getContextPath();
  List<EnrollmentDTO> enrolls = (List<EnrollmentDTO>) request.getAttribute("enrollments");
%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>My Enrollments</title>
  <link rel="stylesheet" href="<%=ctx%>/css/styles.css" />
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <script>
    function confirmDeEnroll() { return confirm('Are you sure you want to de-enroll from this course?'); }
  </script>
</head>
<body>
  <div class="container">
    <div class="header">
      <div class="brand">
        <div class="logo">C</div>
        <div>
          <div class="title">Course Enrollment</div>
        </div>
      </div>
      <div class="nav">
        <a href="<%=ctx%>/courses">Courses</a>
        <a href="<%=ctx%>/my-enrollments">My Enrollments</a>
      </div>
    </div>

    <div class="card">
      <h3>My Enrollments</h3>
      <table class="table">
        <thead>
          <tr><th>Course</th><th>Code</th><th>Fee</th><th>Enrolled On</th><th>Status</th><th>Action</th></tr>
        </thead>
        <tbody>
        <%
          if (enrolls != null && !enrolls.isEmpty()) {
            for (EnrollmentDTO e : enrolls) {
        %>
          <tr>
            <td><%= e.getCourseTitle() %></td>
            <td><%= e.getCourseCode() %></td>
            <td>₹<%= e.getFee() %></td>
            <td><%= e.getEnrollDate() %></td>
            <td>
              <% if ("enrolled".equalsIgnoreCase(e.getStatus())) { %>
                <span class="badge enrolled">enrolled</span>
              <% } else { %>
                <span class="badge"><%= e.getStatus() %></span>
              <% } %>
            </td>
            <td>
              <form method="post" action="<%=ctx%>/enroll" onsubmit="return confirmDeEnroll();" style="display:inline;">
                <input type="hidden" name="action" value="deenroll"/>
                <input type="hidden" name="courseId" value="<%= e.getCourseId() %>"/>
                <button class="btn ghost" type="submit">De-enroll</button>
              </form>
            </td>
          </tr>
        <%    }
          } else { %>
          <tr><td colspan="6" style="text-align:center;color:var(--muted);padding:18px;">You have no enrollments.</td></tr>
        <% } %>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
