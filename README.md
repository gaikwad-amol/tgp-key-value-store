
# Napkin Math

## TCP echo server
	TCP echo server -> 10 uSec Latency
  
	? req -> 1 sec
	1 req -> (0.000001 * 10) sec = 0.00001 sec
	
	? req -> 1/ 0.00001
	? req -> 1,00,000 per sec
  
	TCP server can make 1,00,000 per sec

---------------------------------------
## GET Operation
	read from memtable -> Random memory R/W -> 50 nS
	(not in scope) if not found, read from disk


	? req -> 1 sec
	1 req -> (0.000001 * 0.001 * 50) sec = 0.00000005 sec
	1/0.00000005 = 2,00,00,000 req
	
	Hence, Random Memory Read (64 bytes) -> 2 millions RPS
	But as the TCP calls is 1,00,000 RPS, so maximum GET is 1,00,000 RPS

---------------------------------------
## SET Operation
	1 WAL insert (with fsync for durability)
	1 memtable insert
	
	Sequential SSD write, +fsync -> 1 ms
	Random Memory R/W (64 bytes) -> 50 ns
	
	1 req -> 0.001 sec
	? req -> 1 sec
	? = 1/0.001 = 1000
	
	Sequential SSD write -> 1000 RPS
	Random memory write -> 2 millions RPS
	
	The SSD write is 1000 RPS (ignoring the memory write as the SSD write is higher)
