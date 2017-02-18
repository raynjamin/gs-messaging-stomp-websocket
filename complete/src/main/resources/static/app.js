var stompClient = null;
var currentUser = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        name: $("#name").val()
    }, function (frame) {
        var urlparts = socket._transport.url.split("/");

        currentUser = urlparts[urlparts.length - 2];

        setConnected(true);
        console.log('Connected: ' + frame);

        // subscribed to user events
        stompClient.subscribe('/user/' + currentUser + '/', function (response) {
            showMessage(JSON.parse(response.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }

    setConnected(false);
    console.log("Disconnected");
}

function sendCommand() {
    stompClient.send("/game/user-input", {}, JSON.stringify({
        type: "chat",
        value: $("#command").val()
    }));
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message.user.name + ":" + message.value + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#connect" ).click(connect);
    $( "#disconnect" ).click(disconnect);
    $( "#send" ).click(sendCommand);
});

