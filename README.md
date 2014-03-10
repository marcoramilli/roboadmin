![alt text](http://roboadmin.sourceforge.net/img/alto.jpg "Roboadmin, with the courtesy of amazon")

RoboAdmin [![Support Marco via Gittip](http://img.shields.io/gittip/marco.svg)](https://www.gittip.com/marco/)
===========

### Web Site
The RoboAdmin website is loceted here: [RoboAdmin](http://roboadmin.sourceforge.net)

### Description

Remote system's administration is usually performed according to the standard client-server model. A service runs on the target system, either providing a remote view of the locally available administration tools (e.g: remote terminal, remote desktop), or implementing a back-end for the execution of complex commands received through a corresponding front-end (e.g.: web-based administration interfaces). The service usually exposes a single access point, which is the obvious target of attacks like, for instance: DoS or brute-force authentication attempts. Limiting the impact of these attacks is very difÞcult; usually one or more reactive or proactive techniques are exploited, as brießy summarized hereinafter. Access limitation solutions, like for example account lock-out and connection throttling, react to sus- pect activity (in terms of failed login attempts or exceedingly high trafÞc) that could be the symptom of an ongoing attack. In this way, however, they expose the legitimate administrator as well as the attacker to the same risk of being cut out of the server. Pre-authentication protocols, as port-knocking and Cryptographically Constantly Changing Port Opening (C3PO), tackle instead the problem of hiding the administration port to everyone but the legitimate administrator; the server recognizes a sequence of specially crafted packets sent by the administrator, and subsequently allows the originating address to access the administration port. The sequence is based on a shared secret, and usually involves sending several packets to the correct TCP or UDP ports within a time-frame. This approach also suffers from various drawbacks: Þrst, temporary lock-out can again be triggered by any malicious or fortuitous event that lets the server receive a wrong sequence; second, a special client is required to execute the protocol; third, trafÞc directed to unusual ports could be blocked before it reaches the server. Host- and network-based intrusion detection systems can help thwarting the attacks before they suc- ceed, but can not guarantee security against the vastly distributed attacks that are presently possible, especially considering the value of the target (that is full control of an Internet host). However, there is a signiÞcant difference between the administration service and the others usually provided by the same host. While the latter must typically be fairly visible to a varied audience, the former is intended solely for the legitimate system administrator. This peculiarity can be leveraged to protect the sensitive administration service from malicious exploitation attempts, by completely changing the access model. The goal of this research is to devise an unconventional model of communication between the system administrator and the remote administration interface. In the proposed solution, previously outlined in, the intrinsic vulnerability of the traditional scheme is addressed by reversing the client-server relation; an administration engine replaces the classical service, originating connections to an intermediate system rather then listening for connections. The immediate advantage arising from this design choice is that there is nothing to attack on the remote host. On the other hand, the introduction of an additional system in the security chain must be carefully evaluated, to avoid introducing unexpected attack paths, and eventually making the system less robust than it originally was. We claim that, if properly modeled and implemented, a platform based on the meeting of the server and its administrator on an intermediate system is expedient in terms of security, availability, usability and opportunity for future extension.

![alt text](http://roboadmin.sourceforge.net/img/RoboAdmin.jpg "Roboadmin, with the courtesy of amazon")

### Scientific Papers:


![A messaging-based system for remote server administration](http://roboadmin.sourceforge.net/documents/messageBased.pdf) 

![RoboAdmin: a different approach to remote system administration](http://roboadmin.sourceforge.net/documents/ra-WOSIS-v05.pdf) 

![messaging-based system for remote server administration](http://roboadmin.sourceforge.net/documents/RA-nss.pdf)


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/marcoramilli/roboadmin/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

