CDATAwithSOAP
=============

Using CDATA with SOAP xml message

For one of my recent projects, I had to use CDATA (as data part) in SOAP web service message.
We are using WxtxInputFactory to handle the SOAP xml message.

Faced the issue of CDATA section getting skipped while processing. 
Came up with the attached code to deal with the same.
The same kind of SOAP message is supposed to be used for different web services. 
So added a more generic solution to return a list of elements from the incoming SOAP message irrespective of the object it binds to.
Adding the basic code template to deal with the above case.
Adding to Git account for my future reference.
