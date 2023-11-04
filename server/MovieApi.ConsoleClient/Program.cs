// See https://aka.ms/new-console-template for more information

using Microsoft.AspNetCore.SignalR.Client;

Console.WriteLine("Hello, World!");

var connection = new HubConnectionBuilder().WithUrl("http://localhost:5191/GameHub").Build();

connection
    .StartAsync()
    .ContinueWith(connectTask =>
    {
        if (connectTask.IsFaulted)
        {
            Console.Error.WriteLine(
                $"There was an error opening the connection: {connectTask.Exception!.GetBaseException()}"
            );
            return;
        }

        Console.WriteLine("Connected");

        connection.Closed += _ =>
        {
            Console.WriteLine("Disconnected");
            return Task.CompletedTask;
        };

        connection.On<string, string>(
            "ReceiveMessage",
            (user, message) =>
            {
                Console.WriteLine($"<{user}>: {message}");
            }
        );

        while (true)
        {
            var message = Console.ReadLine();

            if (string.IsNullOrWhiteSpace(message))
            {
                break;
            }

            connection
                .InvokeAsync<string>("SendMessage", connection.ConnectionId, message)
                .ContinueWith(sendTask =>
                {
                    if (sendTask.IsFaulted)
                    {
                        Console.Error.WriteLine(
                            $"There was an error calling send: {sendTask.Exception!.GetBaseException()}"
                        );
                    }
                });
        }
    })
    .Wait();

Console.Read();
connection.StopAsync().Wait();
