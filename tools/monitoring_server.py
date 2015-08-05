import time
import BaseHTTPServer


HOST_NAME = '127.0.0.1'
PORT_NUMBER = 8088

HTML = """
<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['annotationchart']}]}"></script>
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">
    
    // Load the Visualization API and the annotationchart package.
    google.load('visualization', '1', {'packages':['annotationchart']});
      
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);
      
    function drawChart() {
      var jsonData = $.ajax({
          url: "getData",
          dataType:"json",
          async: false
          }).responseText;
          
      var evalledData = eval("("+jsonData+")");
      // Create our data table out of JSON data loaded from server.
      var data = new google.visualization.DataTable(evalledData);

      // Instantiate and draw our chart, passing in some options. 
      var chart = new google.visualization.AnnotationChart(document.getElementById('chart_div'));
      var options = {
        displayAnnotations: false
      };
      chart.draw(data, options);
    }

    </script>
  </head>
  <body>
    <div id='chart_div' style='width: 900px; height: 500px;'></div>
  </body>
</html>
"""

def send_data(s):
  with open('uptimeData.json', 'r') as uptime_file:
    uptime_data = uptime_file.read()
    s.wfile.write(uptime_data)

class MyHandler(BaseHTTPServer.BaseHTTPRequestHandler):
  def do_HEAD(s):
    s.send_response(200)
    s.send_header("Content-type", "text/html")
    s.end_headers()

  def do_GET(s):
    """Respond to a GET request."""
    s.send_response(200)
    s.send_header("Content-type", "text/html")
    s.end_headers()
    if s.path == "/getData":
      send_data(s)
    else:
      s.wfile.write(HTML)
#    s.wfile.write("<html><head><title>Title goes here.</title></head>")
#    s.wfile.write("<body><p>This is a test.</p>")
#    s.wfile.write("<p>You accessed path: %s</p>" % s.path)
#    s.wfile.write("</body></html>")

def main():
  server_class = BaseHTTPServer.HTTPServer
  httpd = server_class((HOST_NAME, PORT_NUMBER), MyHandler)
  print time.asctime(), "Server Starts - %s:%s" % (HOST_NAME, PORT_NUMBER)
  try:
      httpd.serve_forever()
  except KeyboardInterrupt:
      pass
  httpd.server_close()
  print time.asctime(), "Server Stops - %s:%s" % (HOST_NAME, PORT_NUMBER)

if __name__ == '__main__':
  main()
