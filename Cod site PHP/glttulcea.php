<?php

$timeout = 3;  
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
$host = '192.168.0.103';
$port = 50007;

$error = NULL;
$attempts = 0;
$timeout *= 1000;
$connected = FALSE;
while (!($connected = socket_connect($socket, $host, $port)) && ($attempts++ < $timeout)) 
{
    $error = socket_last_error();
    if ($error != SOCKET_EINPROGRESS && $error != SOCKET_EALREADY) 
    {
        echo "Error Connecting Socket: ".socket_strerror($error) . "\n";
        socket_close($socket);
        return NULL;
    }
    usleep(1000);
}

if (!$connected) 
{
    echo "Error Connecting Socket: Connect Timed Out After " . $timeout/1000 . " seconds. ".socket_strerror(socket_last_error()) . "\n";
    socket_close($socket);
    return NULL;
}

// Write to the socket
$output="Client Logged on via website" ;
socket_write($socket, $output, strlen ($output)) or die("Could not write output\n");

// Get the response from the server - our current telemetry
$raspuns = "";

while($d = socket_read($socket, 1024)){
    $raspuns = $raspuns.$d;
}

echo($raspuns);

socket_close($socket);