// Create a new SockJS connection to the /ws endpoint
const socket = new SockJS('/ws');
// Create a STOMP client over the SockJS connection
const stompClient = Stomp.over(socket);
// Connect the STOMP client
stompClient.connect({}, function (frame) {
    // Log the connection frame
    console.log('Connected: ' + frame);
    // Subscribe to the /topic/newsDeleted topic

    stompClient.subscribe('/topic/newsDeleted', function (message) {

        // Refresh the page
        window.location.reload();

    });
});