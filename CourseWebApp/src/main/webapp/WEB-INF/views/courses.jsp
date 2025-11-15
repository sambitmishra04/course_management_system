<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.Set" %>
<%@ page import="com.course.model.Course" %>
<%
    String ctx = request.getContextPath();
    String msg = request.getParameter("msg");
    String err = request.getParameter("error");
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    Set<Integer> enrolledCourseIds = (Set<Integer>) request.getAttribute("enrolledCourseIds");
    boolean loggedIn = (session != null && session.getAttribute("student") != null);
%>
<!doctype html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Courses - Course Enrollment</title>
  <link rel="stylesheet" href="<%=ctx%>/css/styles.css" />
  <meta name="viewport" content="width=device-width,initial-scale=1"/>
  <script>
    function confirmDeEnroll() {
      return confirm('Are you sure you want to de-enroll from this course?');
    }
  </script>
</head>
<body>
  <div class="container">
    <div class="header">
      <div class="brand">
        <div class="logo">C</div>
        <div>
          <div class="title">Course Enrollment</div>
          <div style="font-size:12px;color:var(--muted)">Browse courses & manage enrollments</div>
        </div>
      </div>

      <!-- NAV: shows My Enrollments when logged in -->
      <div class="nav">
        <a href="<%=ctx%>/courses">Courses</a>
        <% if (!loggedIn) { %>
          <a href="<%=ctx%>/login.jsp">Login</a>
          <a href="<%=ctx%>/register.jsp">Register</a>
        <% } else { %>
          <a href="<%=ctx%>/my-enrollments">My Enrollments</a>
          <form method="post" action="<%=ctx%>/auth" style="display:inline;">
            <input type="hidden" name="action" value="logout"/>
            <button class="btn ghost" type="submit">Logout</button>
          </form>
        <% } %>
      </div>
    </div>

    <% if (msg != null) { %>
      <div class="card"><p style="color:var(--success)"><%=msg%></p></div>
    <% } else if (err != null) { %>
      <div class="card"><p style="color:var(--danger)"><%=err%></p></div>
    <% } %>

    <div class="card">
      <h3 style="margin-bottom:10px">Available Courses</h3>
      <table class="table">
        <thead>
          <tr><th>Code</th><th>Title</th><th>Fee</th><th>Seats</th><th>Action</th></tr>
        </thead>
        <tbody>
        <%
          if (courses != null && !courses.isEmpty()) {
            for (Course c : courses) {
              boolean isEnrolled = (enrolledCourseIds != null && enrolledCourseIds.contains(c.getCourseId()));
        %>
          <tr>
            <td><%=c.getCode()%></td>
            <td><%=c.getTitle()%></td>
            <td>₹<%=c.getFee()%></td>
            <td>
              <% if (c.getSeats() <= 0) { %>
                <span class="badge full">Full</span>
              <% } else { %>
                <%=c.getSeats()%>
              <% } %>
            </td>
            <td>
              <% if (!loggedIn) { %>
                <a href="<%=ctx%>/login.jsp" class="btn secondary">Login to enroll</a>
              <% } else { 
                   if (isEnrolled) { %>
                     <form method="post" action="<%=ctx%>/enroll" style="display:inline;" onsubmit="return confirmDeEnroll();">
                       <input type="hidden" name="action" value="deenroll"/>
                       <input type="hidden" name="courseId" value="<%=c.getCourseId()%>"/>
                       <button class="btn ghost" type="submit">De-enroll</button>
                     </form>
                   <% } else { 
                       if (c.getSeats() > 0) { %>
                         <form method="post" action="<%=ctx%>/enroll" style="display:inline;">
                           <input type="hidden" name="action" value="enroll"/>
                           <input type="hidden" name="courseId" value="<%=c.getCourseId()%>"/>
                           <button class="btn" type="submit">Enroll</button>
                         </form>
                       <% } else { %>
                         <span class="badge full">Full</span>
                       <% } 
                     } 
                 } %>
            </td>
          </tr>
        <%    }
          } else { %>
          <tr><td colspan="5" style="text-align:center; padding:18px; color:var(--muted)">No courses available.</td></tr>
        <% } %>
        </tbody>
      </table>
    </div>

    <% if (loggedIn) {
        com.course.model.Student s = (com.course.model.Student) session.getAttribute("student");
    %>
      <div style="margin-top:10px; text-align:right; color:var(--muted);">
        Logged in as <strong><%=s.getFullName() != null ? s.getFullName() : s.getUsername()%></strong>
      </div>
    <% } %>

    <p style="text-align:center; color:var(--muted); margin-top:18px;">© <%=java.time.Year.now()%> Course Enrollment</p>
  </div>
</body>
</html>
