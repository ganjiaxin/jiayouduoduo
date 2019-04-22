<%@ page contentType="text/html;charset=UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<!-- HTML for static distribution bundle build -->
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Swagger UI</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/swagger/swagger-ui.css" >
    <link rel="icon"  href="<%=basePath%>/static/swagger/favicon-32x32.png" sizes="32x32" />
    <link rel="icon"  href="<%=basePath%>/static/swagger/favicon-16x16.png" sizes="16x16" />
    <style>
      html
      {
        box-sizing: border-box;
        overflow: -moz-scrollbars-vertical;
        overflow-y: scroll;
      }

      *,
      *:before,
      *:after
      {
        box-sizing: inherit;
      }

      body
      {
        margin:0;
        background: #fafafa;
      }
    </style>
  </head>

  <body>
    <div id="swagger-ui"></div>

    <script src="<%=basePath%>/static/swagger/swagger-ui-bundle.js"> </script>
    <script src="<%=basePath%>/static/swagger/swagger-ui-standalone-preset.js"> </script>
    <script>
      window.onload = function() {
        // Build a system
        const ui = SwaggerUIBundle({
          url: "<%=basePath%>/v2/api-docs",
          dom_id: '#swagger-ui',
          deepLinking: true,
          presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
          ],
          plugins: [
            SwaggerUIBundle.plugins.DownloadUrl
          ],
          layout: "StandaloneLayout"
        })}
  </script>
  </body>
</html>
