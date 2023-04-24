# Queue-Management-Application

I designed in Java a Queues Management application that simulates the arrival and service of a series of N clients in Q queues. The simulation is defined by a simulation time 𝑡𝑠𝑖𝑚𝑢𝑙𝑎𝑡𝑖𝑜𝑛. All clients are generated when the simulation starts and are identified by three parameters: ID (a number between 1 and N), 𝑡𝑎𝑟𝑟𝑖𝑣𝑎𝑙 (the simulation time when they are ready to enter the queue), and 𝑡𝑠𝑒𝑟𝑣𝑖𝑐𝑒 (the time required to serve the client; i.e., the waiting time when the client is at the front of the queue). The application tracks the total time each client spends in the queues and calculates the average waiting time, service time and peak hour. Clients are added to the queue with the shortest waiting time when their 𝑡𝑎𝑟𝑟𝑖𝑣𝑎𝑙 time is greater than or equal to the simulation time (𝑡𝑎𝑟𝑟𝑖𝑣𝑎𝑙 ≥ 𝑡𝑠𝑖𝑚𝑢𝑙𝑎𝑡𝑖𝑜𝑛).

The application uses layered design: the classes are structured into packages: GUI (SimulationFrame), Model (Client and Server) and BusinessLogic (SimulationManager, Scheduler, ConcreteStrategyQueue and ConcreteStrategyTime that implement the Strategy interface). The Graphical User Interface is user-friendly and allows the user to set the values for the simulation. When the simulation starts, a new frame displays the live reslt of clients and queues.

The Queue Management application uses threads and synchronization mechanisms improve performance, offer modularity and code organization by allowing the program to be broken down into smaller, more manageable pieces, with each piece running on a separate thread, and also provides resource sharing and scalability.

The GUI of the application:

![Screenshot 2023-04-24 211211](https://user-images.githubusercontent.com/115418520/234087462-7bb001be-3edf-411a-bc9f-172915777e15.jpg)

The frame that displays the info at the end of the simulation:

![Screenshot 2023-04-24 211633](https://user-images.githubusercontent.com/115418520/234087562-d99759d7-4efa-4887-9417-d1912ce2783e.jpg)


I provided three log files (test1, test2, test3) that contain the test results for the inputs below:

![Screenshot 2023-04-23 213344](https://user-images.githubusercontent.com/115418520/234087895-b636163f-1cad-4a7d-9a43-cb86eb1ad653.jpg)



