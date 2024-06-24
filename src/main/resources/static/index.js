const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/newsDeleted', function (message) {

        // Refresh the page
        window.location.reload();

    });
});