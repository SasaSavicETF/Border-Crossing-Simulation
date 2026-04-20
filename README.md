This project simulates border crossing for different types of vehicles including cars, trucks and buses. Vehicles are passing through 2 types of terminals (Police Terminal and Border Terminal).
Vehicles are generated randomly and placed in a queue which works as a thread. A vehicle can approach a terminal only if the one at the front did go through.
Each terminal can be closed at any time using the configuration file named 'konfiguracija_terminala.txt'. A custom Java File Watcher is used to track changes in the mentioned file and apply those changes in real time.
If a terminal is closed, vehicles cannot pass through and have to wait in queue. Additional details can be seen in the source code. 
Java Swing is used for UI that helps track all the vehicles and terminals.
