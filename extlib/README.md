# External Libraries for CliffieGame

Cliffiegame requires LWJGL and PNGDecoder.  These libraries should be placed in
this directory.

## Installation of LWJGL
1. Download LWJGL (2.8.4 or better) from: 
	- http://www.lwjgl.org/download.php
2. Install the following .jar files into this directory:
	- lwjgl.jar
	- lwjgl_util.jar
	- jinput.jar
	- lzma.jar
3. Install the natives for your platform (.dll files for Windows, .so files for Linux) into:
	- natives/

## Installation of PNGDecoder
1. Download PNGDecoder.jar from:
	- http://twl.l33tlabs.org/#downloads
2. Install it into this directory.

## Update Eclipse to point to these jar files (optional)
1. Eclipse should see the above libraries with no problem.  However, if it does not for some reason, update your classpath as follows:
	- ... [TODO]

