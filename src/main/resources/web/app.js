var socket = new WebSocket("ws://" + window.location.host + "/ws");
socket.onclose = function() {
  console.log("closed");
};
socket.onopen = function() {
  console.log("connected");
  socket.send("hello");
  // socket.close();
};
setTimeout(function() { socket.close() }, 5000);
