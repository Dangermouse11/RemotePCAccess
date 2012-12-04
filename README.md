RemotePCAccess
================
A simple remote PC access tool for Windows that receives commands through email messages using the IMAP protocol.

* Author: Ben Katz (<bakatz@vt.edu>)
* Bug tracker: <https://github.com/bakatz/RemotePCAccess/issues>
* Installation instructions:

1. Package the root folder as a jar file, including the library `mail.jar` -- OR use the precompiled .jar in the `bin` directory.
1. Run the .jar file. Enter email, password, and IMAP mail info
1. Click start listening. RPA will automatically check the mailbox every 3 seconds for new messages.
1. Send an email to the email you provided with a command and password attached. The expected password is `opensesame` and the supported commands are `lock,password`, `shutdown,password`, `restart,password`.
1. Verify that the command worked on the host machine. Done.

Note: Each of these commands must be entered as the subject of the email. For example, to shut down the computer this .jar is running on, send an email to the email address you typed in with the subject `shutdown,opensesame`. It's very important that no one knows this email as anyone could remotely turn off your computer after brute-forcing the password.

Future updates: this tool was created for my personal use and will likely not be extended much further as it already does everything I need it to do.
