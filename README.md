# sicxe-sim-mvn

SIC/XE simulator and assembler as RESTful service/website
=========================================================

A [link to application] (http://sicxe.elasticbeanstalk.com)  
Introduction and technologies
-----------------------------
This project was realized as a seminar assignement for course System Software and Web Programming.
Backend (server) of application is implemented in Java 8 with Spring framework. 
It uses Tomcat as web container, mysql database for persistance and Amazon S3 service for file storage. Maven and Spring Boot are used to build application. Client is developed in AngularJS, HTML5 and CSS3. 
Application server is hosted in Amazon Cloud, in service called Elastic BeanStalk. Client and serverside of application follows MVC pattern.

Communication
-------------
Communication between client and server  is done using HTTP.  Application uses AJAX for loading dynamic content, which prevents reloading whole document – only part that changes is reloaded. Thanks to this, html templates are sent only once to the client, on the first request to concrete page, and then they are cached in browser. Data is sent from/to client using lightweight JSON format. Server provides REST API (not documented though) for loading assembly files (client as a response gets memory and disassembled instructions) and processing instructions step by step (client gets memory, registers, and disassembled instruction). It wasn't said explicitly but it is worth noting that each client (actually each session) gets his own machine (SICXE unit) on the server side. This is accomplished using multithreading offered by Spring. Additionaly data sent to client is only the difference between machine state that was sent before and the actual state (we can't send 2^20 bytes+ of memory in each http response)

SIC/XE model
------------
The core of application is SIC/XE (Simplified Instructional Computer, Extra Equipment)  model which is described in Leland Beck's book System Software. It consists of 9 registers and 2^20 bytes of memory. My implementation handles cca. 70 instruction (out of cca. 90 described in the book). Except machine there is also assembler which translates asm files (that contains mnemonics of real instruction and assembler directives) into object files and absolute loader that loads object code into machine memory. To make instructions executed by SIC/XE understandable for humans, disassembler is also implemented . 

What can be improved
---------------------------
In SICXE simulator/assembler:  
* I haven't used any external tools for parsing. Current implementation is very sensitive to whitespaces and operands formats. Input program is required to follow very strict syntax rules to be succesfully parsed and assembled (e.g. no whitespaces in "operand,X"). This inconvenience could be reduced to minimum if I would use external tools like flex and bison. 
* Only few basic assembler directives (BYTE, WORD, RESW, RESB, START, STOP) are implemented. Implementing others would improve experience from programming.
* Some SICXE instructions need to be implemented.

In web application in general:  
* Security doesn't exist. User forms aren't encrypted (passwords aren't encrypted!). HTTPS is not used. Everyone has access to all contextes and methods on the server side. This needs complete overhaul, but would require to read cca. 1000 pages from Spring Security reference. Also this would require to add a lot of clientside logic to act well after unauthorized access.
* JavaScript files and css should be minified to provide better response times

What have I learned
-------------------
* Parsing is difficult without specialized tools.
* RESTful service decouples backend from fronted, but adds a lots of work on the client side. Angular shouldn't be used for simple sites. It would be faster to make view using serverside templates. 
* This is the largest project that I have done so far and it has some pretty complicated logic that required a lot of testing (parsing, assembling, executing, disassembling). Also, in this project I have done some unit tests (I am faaar from testing every line of code!), and they helped me alot while changing existing implementations. Tests are important and give confidence to change.
