import time
import BaseHTTPServer


HOST_NAME = '127.0.0.1'
PORT_NUMBER = 8088

HTML = """
<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['annotationchart']}]}"></script>
    <script type='text/javascript'>
      google.load('visualization', '1', {'packages':['annotationchart']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('date', 'Date');
        data.addColumn('number', 'Kepler-22b mission');
        data.addColumn('string', 'Kepler title');
        data.addColumn('string', 'Kepler text');
        data.addColumn('number', 'Gliese 163 mission');
        data.addColumn('string', 'Gliese title');
        data.addColumn('string', 'Gliese text');
        data.addRows([
          [new Date(2314, 2, 15), 12400, undefined, undefined,
                                  10645, undefined, undefined],
          [new Date(2314, 2, 16), 24045, 'Lalibertines', 'First encounter',
                                  12374, undefined, undefined],
          [new Date(2314, 2, 17), 35022, 'Lalibertines', 'They are very tall',
                                  15766, 'Gallantors', 'First Encounter'],
          [new Date(2314, 2, 18), 12284, 'Lalibertines', 'Attack on our crew!',
                                  34334, 'Gallantors', 'Statement of shared principles'],
          [new Date(2314, 2, 19), 8476, 'Lalibertines', 'Heavy casualties',
                                  66467, 'Gallantors', 'Mysteries revealed'],
          [new Date(2314, 2, 20), 0, 'Lalibertines', 'All crew lost',
                                  79463, 'Gallantors', 'Omniscience achieved']
        ]);

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
</html>"""

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
